package com.lazy.netty.proxy.client.proxy;

import com.lazy.netty.proxy.client.proxy.config.ClientProxyConfigurationProperties;
import com.lazy.netty.proxy.client.proxy.netty.Constant;
import com.lazy.netty.proxy.client.proxy.netty.ProxySocket;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NettyProxyAutoConfiguration {

    private final ServerProperties serverProperties;
    private final ClientProxyConfigurationProperties clientProxyConfigurationProperties;

    public NettyProxyAutoConfiguration(ServerProperties serverProperties, ClientProxyConfigurationProperties clientProxyConfigurationProperties) {
        this.serverProperties = serverProperties;
        this.clientProxyConfigurationProperties = clientProxyConfigurationProperties;
    }

    @PostConstruct
    public void clientRun() {
        new Thread(() -> {
            Constant.serverIp = clientProxyConfigurationProperties.getServerIp();
            Constant.serverPort = clientProxyConfigurationProperties.getServerPort();
            Constant.realPort = serverProperties.getPort();


                log.info("netty客户端连接服务端IP:{},服务端端口:{},客户端自己的端口:{}",Constant.serverIp,Constant.serverPort,Constant.realPort);
            // 连接代理服务
            try {
                ProxySocket.connectProxyServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).run();
    }

}
