package wu.framework.lazy.cloud.heartbeat.common.encoder;

import wu.framework.lazy.cloud.heartbeat.common.NettyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @see NettyProxyMsg
 * NettyProxyMsg 对象编码
 */
@Deprecated
public class NettMsgEncoder extends MessageToByteEncoder<NettyMsg> {

    public NettMsgEncoder() {

    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMsg nettyMsg, ByteBuf out) throws Exception {
        // type 1   data 4  clientId 4
        int bodyLength = NettyMsg.bodyLength;
        byte[] clientIdBytes = nettyMsg.getClientId();

        if (nettyMsg.getData() != null) {
            bodyLength += nettyMsg.getData().length;
        }
        if (nettyMsg.getClientId() != null) {
            bodyLength += nettyMsg.getClientId().length;
        }

        out.writeInt(bodyLength);

        out.writeByte(nettyMsg.getType());
        // 客户端ID
        // 防止数据读错位置
        if (clientIdBytes != null) {
            out.writeInt(clientIdBytes.length);
            out.writeBytes(clientIdBytes);
        }else {
            // 防止客户端ID未填写
            out.writeInt(0x00);
        }
        if (nettyMsg.getData() != null) {
            out.writeBytes(nettyMsg.getData());
        }
    }
}