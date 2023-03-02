package com.lazy.netty.proxy.client.proxy.config;

import com.lazy.netty.proxy.msg.constant.ProxyConfigConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = ProxyConfigConstant.PREFIX)
public class ClientProxyConfigurationProperties {


    /**
     * 服务端口
     */
    private int serverPort = 16001;

    /**
     * 服务IP
     */
    private String serverIp = "127.0.0.1";

}
