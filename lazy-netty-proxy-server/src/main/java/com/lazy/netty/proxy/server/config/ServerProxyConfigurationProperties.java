package com.lazy.netty.proxy.server.config;

import com.lazy.netty.proxy.msg.constant.ProxyConfigConstant;
import lombok.Data;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = ProxyConfigConstant.PREFIX)
public class ServerProxyConfigurationProperties {

    /**
     * 服务代理端口 通过该端口访问客户端服务端口
     * <p>
     * 是否可以将此端口与如下端口融合、通过请求路径进行转发
     *
     * @see ServerProperties#getPort()
     */
    private int visitorPort = 16002;


}
