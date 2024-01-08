package wu.framework.lazy.cloud.heartbeat.common.encoder;

import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @see NettyProxyMsg
 * NettyProxyMsg 编码
 */
public class NettyProxyMsgEncoder extends MessageToByteEncoder<NettyProxyMsg> {
    /**
     * Encode a message into a {@link ByteBuf}. This method will be called for each written message that can be handled
     * by this encoder.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg the message to encode
     * @param out the {@link ByteBuf} into which the encoded message will be written
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyProxyMsg msg, ByteBuf out) {
        // body 长度 type 1  clientId 4  clientTargetIp 4 clientTargetPort 4 visitorPort 4  visitorId 4 data 4
        int bodyLength = NettyProxyMsg.bodyLength;
        byte typeBytes = msg.getType();
        byte[] clientIdBytes = msg.getClientId();
        byte[] clientTargetIpBytes = msg.getClientTargetIp();
        byte[] clientTargetPortBytes = msg.getClientTargetPort();
        byte[] visitorPortBytes = msg.getVisitorPort();
        byte[] visitorIdBytes = msg.getVisitorId();
        byte[] msgDataBytes = msg.getData();


        if (clientIdBytes != null) {
            bodyLength += clientIdBytes.length;
        }
        if (clientTargetIpBytes != null) {
            bodyLength += clientTargetIpBytes.length;
        }
        if (clientTargetPortBytes != null) {
            bodyLength += clientTargetPortBytes.length;
        }
        if (visitorPortBytes != null) {
            bodyLength += visitorPortBytes.length;
        }
        if (visitorIdBytes != null) {
            bodyLength += visitorIdBytes.length;
        }

        if (msgDataBytes != null) {
            bodyLength += msgDataBytes.length;
        }

        out.writeInt(bodyLength);

        out.writeByte(typeBytes);

        // 防止数据读错位置 clientId
        if (clientIdBytes != null) {
            out.writeInt(clientIdBytes.length);
            out.writeBytes(clientIdBytes);
        } else {
            // 防止客户端id 未填写
            out.writeInt(0x00);
        }


        // 防止数据读错位置 clientTargetIp
        if (clientTargetIpBytes != null) {
            out.writeInt(clientTargetIpBytes.length);
            out.writeBytes(clientTargetIpBytes);
        } else {
            // 防止客户端 目标IP未填写
            out.writeInt(0x00);
        }

        // clientTargetPort
        if (clientTargetPortBytes != null) {
            out.writeInt(clientTargetPortBytes.length);
            out.writeBytes(clientTargetPortBytes);
        } else {
            // 防止客户端目标端口未填写
            out.writeInt(0x00);
        }

        // visitorPort
        if (visitorPortBytes != null) {
            out.writeInt(visitorPortBytes.length);
            out.writeBytes(visitorPortBytes);
        } else {
            // 防止客户端 访客端口未填写
            out.writeInt(0x00);
        }

        if (visitorIdBytes != null) {
            out.writeInt(visitorIdBytes.length);
            out.writeBytes(visitorIdBytes);
        } else {
            // 防止客户端 访客ID未填写
            out.writeInt(0x00);
        }
        if (msgDataBytes != null) {
            out.writeBytes(msgDataBytes);
        }
    }
}
