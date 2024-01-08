package wu.framework.lazy.cloud.heartbeat.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 云上云下-云下心跳服务器
 */

@SpringBootApplication
public class MiddlegroundUnderCloudHeartbeatClient {
    public static void main(String[] args) {
        SpringApplication.run(MiddlegroundUnderCloudHeartbeatClient.class,args);
    }
}
