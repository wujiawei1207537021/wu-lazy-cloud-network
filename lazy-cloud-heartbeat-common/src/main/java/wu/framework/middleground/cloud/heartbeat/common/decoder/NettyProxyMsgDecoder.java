package wu.framework.middleground.cloud.heartbeat.common.decoder;

import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.nio.ByteOrder;

/**
 * @see NettyProxyMsg
 * NettyProxyMsg 解码
 */
public class NettyProxyMsgDecoder extends LengthFieldBasedFrameDecoder {
    /**
     * Creates a new instance.
     *
     * @param maxFrameLength    the maximum length of the frame.  If the length of the frame is
     *                          greater than this value, {@link TooLongFrameException} will be
     *                          thrown.
     * @param lengthFieldOffset the offset of the length field
     * @param lengthFieldLength the length of the length field
     */
    public NettyProxyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    /**
     * Creates a new instance.
     *
     * @param maxFrameLength      the maximum length of the frame.  If the length of the frame is
     *                            greater than this value, {@link TooLongFrameException} will be
     *                            thrown.
     * @param lengthFieldOffset   the offset of the length field
     * @param lengthFieldLength   the length of the length field
     * @param lengthAdjustment    the compensation value to add to the value of the length field
     * @param initialBytesToStrip the number of first bytes to strip out from the decoded frame
     */
    public NettyProxyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    /**
     * Creates a new instance.
     *
     * @param maxFrameLength      the maximum length of the frame.  If the length of the frame is
     *                            greater than this value, {@link TooLongFrameException} will be
     *                            thrown.
     * @param lengthFieldOffset   the offset of the length field
     * @param lengthFieldLength   the length of the length field
     * @param lengthAdjustment    the compensation value to add to the value of the length field
     * @param initialBytesToStrip the number of first bytes to strip out from the decoded frame
     * @param failFast            If <tt>true</tt>, a {@link TooLongFrameException} is thrown as
     *                            soon as the decoder notices the length of the frame will exceed
     *                            <tt>maxFrameLength</tt> regardless of whether the entire frame
     *                            has been read.  If <tt>false</tt>, a {@link TooLongFrameException}
     *                            is thrown after the entire frame that exceeds <tt>maxFrameLength</tt>
     *                            has been read.
     */
    public NettyProxyMsgDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    /**
     * Creates a new instance.
     *
     * @param byteOrder           the {@link ByteOrder} of the length field
     * @param maxFrameLength      the maximum length of the frame.  If the length of the frame is
     *                            greater than this value, {@link TooLongFrameException} will be
     *                            thrown.
     * @param lengthFieldOffset   the offset of the length field
     * @param lengthFieldLength   the length of the length field
     * @param lengthAdjustment    the compensation value to add to the value of the length field
     * @param initialBytesToStrip the number of first bytes to strip out from the decoded frame
     * @param failFast            If <tt>true</tt>, a {@link TooLongFrameException} is thrown as
     *                            soon as the decoder notices the length of the frame will exceed
     *                            <tt>maxFrameLength</tt> regardless of whether the entire frame
     *                            has been read.  If <tt>false</tt>, a {@link TooLongFrameException}
     *                            is thrown after the entire frame that exceeds <tt>maxFrameLength</tt>
     *                            has been read.
     */
    public NettyProxyMsgDecoder(ByteOrder byteOrder, int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(byteOrder, maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
    }

    /**
     * Create a frame out of the {@link ByteBuf} and return it.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in2 the {@link ByteBuf} from which to read data
     * @return frame           the {@link ByteBuf} which represent the frame or {@code null} if no frame could
     * be created.
     */
    @Override
    protected NettyProxyMsg decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        // 解码顺序 body 长度 type 1  clientId 4  clientTargetIp 4 clientTargetPort 4 visitorPort 4  visitorId 4 data 4
        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < 4) {
            return null;
        }

        NettyProxyMsg nettyProxyMsg = new NettyProxyMsg();
        int bodyLength = in.readInt();
        byte type = in.readByte();
        nettyProxyMsg.setType(type);

        int clientIdLength = in.readInt();
        byte[] clientIdBytes = new byte[clientIdLength];
        in.readBytes(clientIdBytes);
        nettyProxyMsg.setClientId(clientIdBytes);

        int clientTargetIpLength = in.readInt();
        byte[] clientTargetIpBytes = new byte[clientTargetIpLength];
        in.readBytes(clientTargetIpBytes);
        nettyProxyMsg.setClientTargetIp(clientTargetIpBytes);


        int clientTargetPortLength = in.readInt();
        byte[] clientTargetPortBytes = new byte[clientTargetPortLength];
        in.readBytes(clientTargetPortBytes);
        nettyProxyMsg.setClientTargetPort(clientTargetPortBytes);

        int visitorPortLength = in.readInt();
        byte[] visitorPortBytes = new byte[visitorPortLength];
        in.readBytes(visitorPortBytes);
        nettyProxyMsg.setVisitorPort(visitorPortBytes);


        int visitorIdLength = in.readInt();
        byte[] visitorIdBytes = new byte[visitorIdLength];
        in.readBytes(visitorIdBytes);
        nettyProxyMsg.setVisitorId(visitorIdBytes);

        byte[] data = new byte[bodyLength - NettyProxyMsg.bodyLength -
                clientIdLength -
                clientTargetIpLength -
                clientTargetPortLength -
                visitorPortLength -
                visitorIdLength];
        in.readBytes(data);
        nettyProxyMsg.setData(data);
        in.release();

        return nettyProxyMsg;
    }
}
