package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;


import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelFlowAdapter;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.AbstractHandleReportHandleChannelTransferTypeAdvanced;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.enums.ChannelFlowEnum;
import wu.framework.lazy.cloud.heartbeat.server.netty.flow.ServerChannelFlow;


/**
 * 服务端处理客户端数据传输
 * REPORT_CLIENT_STAGING_CLOSED
 */
@Slf4j
@Component
public class ServerHandleReportHandleChannelTransferTypeAdvanced extends AbstractHandleReportHandleChannelTransferTypeAdvanced<NettyProxyMsg> {

    private final ChannelFlowAdapter channelFlowAdapter;

    public ServerHandleReportHandleChannelTransferTypeAdvanced(ChannelFlowAdapter channelFlowAdapter) {
        this.channelFlowAdapter = channelFlowAdapter;
    }

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    public void doHandler(Channel channel, NettyProxyMsg msg) {
        String clientId = new String(msg.getClientId());
        Integer visitorPort = Integer.valueOf(new String(msg.getVisitorPort()));
        log.debug("接收到客户端:[{}]内网穿透返回的数据:[{}]", clientId, new String(msg.getData()));
        // 将数据转发访客通道
        byte[] visitorId = msg.getVisitorId();
        Channel visitor = NettyRealIdContext.getReal(visitorId);
        if (visitor != null) {
            ByteBuf buf = visitor.config().getAllocator().buffer(msg.getData().length);
            buf.writeBytes(msg.getData());
            visitor.writeAndFlush(buf);

            // 记录出口数据
            ServerChannelFlow serverChannelFlow = ServerChannelFlow
                    .builder()
                    .channelFlowEnum(ChannelFlowEnum.OUT_FLOW)
                    .port(visitorPort)
                    .clientId(clientId)
                    .flow(msg.getData().length)
                    .build();
            channelFlowAdapter.handler(channel,serverChannelFlow);
        }

    }

}
