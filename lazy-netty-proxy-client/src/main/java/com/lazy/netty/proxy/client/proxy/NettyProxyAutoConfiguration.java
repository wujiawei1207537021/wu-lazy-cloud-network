package com.lazy.netty.proxy.client.proxy;

import com.lazy.netty.proxy.client.proxy.config.ClientProxyConfigurationProperties;
import com.lazy.netty.proxy.client.proxy.netty.Constant;
import com.lazy.netty.proxy.client.proxy.netty.ProxySocket;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class NettyProxyAutoConfiguration {

    private final ServerProperties serverProperties;
    private final ClientProxyConfigurationProperties clientProxyConfigurationProperties;

    public NettyProxyAutoConfiguration(ServerProperties serverProperties, ClientProxyConfigurationProperties clientProxyConfigurationProperties) {
        this.serverProperties = serverProperties;
        this.clientProxyConfigurationProperties = clientProxyConfigurationProperties;
    }

    @Bean
    public Thread xx() {
        return new Thread(() -> {
            Constant.serverIp = clientProxyConfigurationProperties.getServerIp();
            Constant.serverPort = clientProxyConfigurationProperties.getServerPort();
            Constant.realPort = serverProperties.getPort();

            // 连接代理服务
            try {
                ProxySocket.connectProxyServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}
