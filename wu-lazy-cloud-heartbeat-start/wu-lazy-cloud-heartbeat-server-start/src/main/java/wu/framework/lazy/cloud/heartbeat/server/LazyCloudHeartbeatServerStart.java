package wu.framework.lazy.cloud.heartbeat.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 云上云下-云上心跳服务器
 */
@Slf4j
@SpringBootApplication
public class LazyCloudHeartbeatServerStart {
    public static void main(String[] args) {
        SpringApplication.run(LazyCloudHeartbeatServerStart.class,args);
    }
}
