package wu.framework.lazy.cloud.heartbeat.server.netty.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wu.framework.lazy.cloud.heartbeat.server.netty.filter.NettyServerFilter;
import wu.framework.lazy.cloud.heartbeat.server.netty.socket.NettyOnCloudNettyServerSocket;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * description 自动配置
 *
 * @author 吴佳伟
 * @date 2023/09/12 18:22
 */
@Slf4j
@Configuration
public class ServerAutoConfiguration implements CommandLineRunner {

    private final ServerProperties serverProperties;
    private final NettyServerFilter nettyServerFilter;
    ThreadPoolExecutor NETTY_SERVER_EXECUTOR = new ThreadPoolExecutor(1, 1, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1));

    public ServerAutoConfiguration(ServerProperties serverProperties, NettyServerFilter nettyServerFilter) {
        this.serverProperties = serverProperties;
        this.nettyServerFilter = nettyServerFilter;
    }


    @Bean(destroyMethod = "shutdown")
    public NettyOnCloudNettyServerSocket nettyServerSocket(){
        return new NettyOnCloudNettyServerSocket(nettyServerFilter);
    }
    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        NettyOnCloudNettyServerSocket serverSocket = new NettyOnCloudNettyServerSocket(nettyServerFilter);
        log.info("当前服务启动端口:{}", serverProperties.getPort());
        int nettyServerPort = serverProperties.getPort() + 1000;
        Thread thread = new Thread(() -> {
            try {
                serverSocket.startServer(nettyServerPort);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        log.info("当前服务启动Netty端口:{}", nettyServerPort);
        NETTY_SERVER_EXECUTOR.execute(thread);

    }
}
