package wu.framework.lazy.cloud.heartbeat.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 服务端配置信息
 */
@Configuration
@ConfigurationProperties(prefix = HeartbeatProperties.prefix)
@Data
public class HeartbeatProperties {
    public static final String prefix = "spring.lazy.cloud.heartbeat.server";
    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
}
