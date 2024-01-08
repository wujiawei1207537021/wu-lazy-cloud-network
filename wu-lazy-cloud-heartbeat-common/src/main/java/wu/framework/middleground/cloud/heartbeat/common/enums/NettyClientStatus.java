package wu.framework.middleground.cloud.heartbeat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * netty客户端 状态
 */
@AllArgsConstructor
@Getter
public enum NettyClientStatus {

    ON_LINE("在线"),
    OFF_LINE("离线");

    private final String desc;
}
