package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;


import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractHandleChannelHeartbeatTypeAdvanced;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;


/**
 * 服务端 处理客户端心跳
 * TYPE_HEARTBEAT
 */
@Component
public class ServerHandleChannelHeartbeatTypeAdvanced extends AbstractHandleChannelHeartbeatTypeAdvanced<NettyProxyMsg> {

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    public void doHandler(Channel channel, NettyProxyMsg msg) {
        NettyProxyMsg hb = new NettyProxyMsg();
        hb.setType(MessageType.TYPE_HEARTBEAT);
        channel.writeAndFlush(hb);
    }


}
