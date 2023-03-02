package com.lazy.netty.proxy.server.netty;

import com.lazy.netty.proxy.server.Constant;
import com.lazy.netty.proxy.server.ServerSocket;
import com.lazy.netty.proxy.server.VisitorSocket;
import com.lazy.netty.proxy.server.config.ServerProxyConfigurationProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NettyServerProxyAutoConfiguration {

    private final ServerProperties serverProperties;
    private final ServerProxyConfigurationProperties serverProxyConfigurationProperties;

    public NettyServerProxyAutoConfiguration(ServerProperties serverProperties, ServerProxyConfigurationProperties serverProxyConfigurationProperties) {
        this.serverProperties = serverProperties;
        this.serverProxyConfigurationProperties = serverProxyConfigurationProperties;
    }


    @PostConstruct
    public void xx() {
         new Thread(() -> {
            Constant.visitorPort = serverProxyConfigurationProperties.getVisitorPort();
            Constant.serverPort = serverProperties.getPort();

            // 启动访客服务端，用于接收访客请求
            try {
                VisitorSocket.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 启动代理服务端，用于接收客户端请求
            try {
                ServerSocket.startServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).run();
    }

}
