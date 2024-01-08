package wu.framework.middleground.under.cloud.heartbeat.client.netty.config;


import wu.framework.middleground.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.middleground.under.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import wu.framework.middleground.under.cloud.heartbeat.client.netty.socket.NettyClientSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
public class AutoConfiguration implements CommandLineRunner {

    private final ServerProperties serverProperties;
    private final NettyServerProperties nettyServerProperties;
    private final ClientNettyConfigApplication clientNettyConfigApplication;

    private final List<ChannelTypeAdvanced> channelTypeAdvancedList; // 处理服务端发送过来的数据类型


    ThreadPoolExecutor NETTY_CLIENT_EXECUTOR = new ThreadPoolExecutor(1, 1, 200, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(1));

    public AutoConfiguration(ServerProperties serverProperties,
                             NettyServerProperties nettyServerProperties,
                             ClientNettyConfigApplication clientNettyConfigApplication,
                             List<ChannelTypeAdvanced> channelTypeAdvancedList) {
        this.serverProperties = serverProperties;
        this.nettyServerProperties = nettyServerProperties;
        this.clientNettyConfigApplication = clientNettyConfigApplication;
        this.channelTypeAdvancedList = channelTypeAdvancedList;
    }


    @Bean(destroyMethod = "shutdown")
    public NettyClientSocket nettyServerSocket() {
        String inetHost = nettyServerProperties.getInetHost();
        int inetPort = nettyServerProperties.getInetPort();
        String clientId = nettyServerProperties.getClientId();
        return new NettyClientSocket(inetHost, inetPort, clientId, clientNettyConfigApplication, channelTypeAdvancedList);
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
        NettyClientSocket nettyClientSocket = new NettyClientSocket(inetHost, inetPort, clientId, clientNettyConfigApplication, channelTypeAdvancedList);
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
