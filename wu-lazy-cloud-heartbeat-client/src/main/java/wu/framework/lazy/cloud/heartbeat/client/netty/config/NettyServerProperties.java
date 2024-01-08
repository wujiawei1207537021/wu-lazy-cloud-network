package wu.framework.lazy.cloud.heartbeat.client.netty.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * netty 客户服务端地址配置属性
 */
@ConfigurationProperties(prefix = NettyServerProperties.PREFIX, ignoreUnknownFields = true)
@Configuration
@Data
public class NettyServerProperties {
    public static final String PREFIX = "spring.middleground.netty";
    /**
     * 服务端地址
     */
    private String inetHost = "127.0.0.1";
    /**
     * 服务端端口
     */
    private int inetPort = 7001;
    /**
     * 服务端path
     */
    private String inetPath = "middleground-on-cloud-heartbeat-server";
    /**
     * 客户端ID
     */
    private String clientId = "1024";
}
