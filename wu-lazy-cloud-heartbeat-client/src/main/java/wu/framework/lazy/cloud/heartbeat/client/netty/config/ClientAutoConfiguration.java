package wu.framework.lazy.cloud.heartbeat.client.netty.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wu.framework.lazy.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import wu.framework.lazy.cloud.heartbeat.client.netty.socket.NettyClientSocket;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;

import java.util.List;
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
public class ClientAutoConfiguration implements CommandLineRunner {

    private final NettyServerProperties nettyServerProperties;
    private final ClientNettyConfigApplication clientNettyConfigApplication;

    private final List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList; // 处理服务端发送过来的数据类型


    ThreadPoolExecutor NETTY_CLIENT_EXECUTOR = new ThreadPoolExecutor(1, 1, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1));

    public ClientAutoConfiguration(NettyServerProperties nettyServerProperties,
                                   ClientNettyConfigApplication clientNettyConfigApplication,
                                   List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) {
        this.nettyServerProperties = nettyServerProperties;
        this.clientNettyConfigApplication = clientNettyConfigApplication;
        this.handleChannelTypeAdvancedList = handleChannelTypeAdvancedList;
    }


    @Bean(destroyMethod = "shutdown")
    public NettyClientSocket nettyClientSocket() {
        String inetHost = nettyServerProperties.getInetHost();
        int inetPort = nettyServerProperties.getInetPort();
        String clientId = nettyServerProperties.getClientId();
        return new NettyClientSocket(inetHost, inetPort, clientId, clientNettyConfigApplication, handleChannelTypeAdvancedList);
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {


        String inetHost = nettyServerProperties.getInetHost();
        int inetPort = nettyServerProperties.getInetPort();
        String clientId = nettyServerProperties.getClientId();
        NettyClientSocket nettyClientSocket = new NettyClientSocket(inetHost, inetPort, clientId, clientNettyConfigApplication, handleChannelTypeAdvancedList);
        Thread thread = new Thread(() -> {
            try {
                nettyClientSocket.newConnect2Server();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        log.info("当前服务连接Netty客户端:{},Netty端口:{}", inetHost, inetPort);
        NETTY_CLIENT_EXECUTOR.execute(thread);


    }
}
