package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;


import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.AbstractReportChannelTransferTypeAdvanced;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 服务端处理客户端数据传输
 * REPORT_CLIENT_STAGING_CLOSED
 */
@Slf4j
@Component
public class ServerReportChannelTransferTypeAdvanced extends AbstractReportChannelTransferTypeAdvanced<NettyProxyMsg> {

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    public void doHandler(Channel channel, NettyProxyMsg msg) {
        log.debug("接收到客户端:[{}]内网穿透返回的数据:[{}]", new String(msg.getClientId()), new String(msg.getData()));
        // 将数据转发访客通道
        byte[] visitorId = msg.getVisitorId();
        Channel visitor = NettyRealIdContext.getVisitor(visitorId);
        if (visitor != null) {
            ByteBuf buf = visitor.config().getAllocator().buffer(msg.getData().length);
            buf.writeBytes(msg.getData());
            visitor.writeAndFlush(buf);
        }

    }

}
