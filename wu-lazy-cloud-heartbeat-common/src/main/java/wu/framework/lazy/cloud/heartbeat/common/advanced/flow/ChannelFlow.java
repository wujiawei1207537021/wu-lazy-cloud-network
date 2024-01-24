package wu.framework.lazy.cloud.heartbeat.common.advanced.flow;

import wu.framework.lazy.cloud.heartbeat.common.enums.ChannelFlowEnum;

public interface ChannelFlow {

    /**
     * 通道客户端ID
     *
     * @return 通道客户端ID
     */
    String clientId();

    /**
     * 通道使用的端口（服务端访客端口、客户端真实端口）
     *
     * @return 端口
     */
    Integer port();

    /**
     * 通道流量类型
     *
     * @return ChannelFlowEnum
     * @see ChannelFlowEnum
     */
    ChannelFlowEnum channelFlowEnum();

    /**
     * 流量
     * @return 流量
     */
    Integer flow();
}


