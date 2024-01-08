package wu.framework.middleground.cloud.heartbeat.common.state;

import lombok.Data;

/**
 * 客户端在线状态
 */
@Data
public class ClientOnLineState {
    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 在线状态
     */
    private String onLineState;
}
