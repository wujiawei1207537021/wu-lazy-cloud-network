package wu.framework.lazy.cloud.heartbeat.server.netty.flow;

import lombok.Builder;
import lombok.Data;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.ChannelFlow;
import wu.framework.lazy.cloud.heartbeat.common.enums.ChannelFlowEnum;

@Builder
@Data
public class ServerChannelFlow implements ChannelFlow {
    private String clientId;
    private Integer port;
    private ChannelFlowEnum channelFlowEnum;
    private Integer flow;

    /**
     * 通道客户端ID
     *
     * @return 通道客户端ID
     */
    @Override
    public String clientId() {
        return clientId;
    }

    /**
     * 通道使用的端口（服务端访客端口、客户端真实端口）
     *
     * @return 端口
     */
    @Override
    public Integer port() {
        return port;
    }

    /**
     * 通道流量类型
     *
     * @return ChannelFlowEnum
     * @see ChannelFlowEnum
     */
    @Override
    public ChannelFlowEnum channelFlowEnum() {
        return channelFlowEnum;
    }

    /**
     * 流量
     *
     * @return 流量
     */
    @Override
    public Integer flow() {
        return flow;
    }
}
