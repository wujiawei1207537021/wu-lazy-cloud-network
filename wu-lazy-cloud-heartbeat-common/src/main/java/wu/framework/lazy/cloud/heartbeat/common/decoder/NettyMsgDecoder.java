package wu.framework.lazy.cloud.heartbeat.common.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import wu.framework.lazy.cloud.heartbeat.common.NettyMsg;

/**
 * @see NettyMsg
 * NettyMsg 对象解码
 */
@Deprecated
public class NettyMsgDecoder extends LengthFieldBasedFrameDecoder {

    public NettyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                           int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public NettyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment,
                           int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    @Override
    protected NettyMsg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < 4) {
            return null;
        }

        NettyMsg nettyMsg = new NettyMsg();
        int bodyLength = in.readInt();
        byte type = in.readByte();
        nettyMsg.setType(type);

        int clientIdLength = in.readInt();
        byte[] clientId = new byte[clientIdLength];
        in.readBytes(clientId);
        nettyMsg.setClientId(clientId);


        byte[] data = new byte[bodyLength - NettyMsg.bodyLength - clientIdLength];
        in.readBytes(data);
        nettyMsg.setData(data);
        in.release();

        return nettyMsg;
    }
}