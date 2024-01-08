package wu.framework.middleground.on.cloud.heartbeat.server.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 云上云下-云上心跳服务器
 */
@SpringBootApplication
public class MiddlegroundOnCloudHeartbeatServer {
    public static void main(String[] args) {
        SpringApplication.run(MiddlegroundOnCloudHeartbeatServer.class,args);
    }
}
