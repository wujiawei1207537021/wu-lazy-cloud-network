package wu.framework.lazy.cloud.heartbeat.server;

import com.wu.framework.inner.lazy.stereotype.LazyScan;
import org.springframework.context.annotation.ComponentScan;

@LazyScan(scanBasePackages = "wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity")
@ComponentScan(basePackages = "wu.framework.lazy.cloud.heartbeat.server")
public class EnableHeartbeatServerAutoConfiguration {
}
