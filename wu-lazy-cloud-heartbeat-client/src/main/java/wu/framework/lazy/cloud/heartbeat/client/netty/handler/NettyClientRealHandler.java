package wu.framework.lazy.cloud.heartbeat.client.netty.handler;


import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyCommunicationIdContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 来自客户端 真实服务器返回的数据请求
 */
@Slf4j
public class NettyClientRealHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {

        // 客户端发送真实数据到代理了
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        log.debug("接收客户端真实服务数据:{}", new String(bytes));
        String visitorId = ChannelAttributeKeyUtils.getVisitorId(ctx.channel());
        // 访客通信通道 上报服务端代理完成
        Channel visitorChannel = NettyCommunicationIdContext.getVisitor(visitorId);
        NettyProxyMsg returnMessage = new NettyProxyMsg();
        returnMessage.setType(MessageType.REPORT_CLIENT_TRANSFER);
        returnMessage.setVisitorId(visitorId);
        returnMessage.setData(bytes);

        visitorChannel.writeAndFlush(returnMessage);


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String clientId = ChannelAttributeKeyUtils.getClientId(ctx.channel());
        String visitorId = ChannelAttributeKeyUtils.getVisitorId(ctx.channel());
        //  客户端真实通信通道
        Channel visitor = NettyCommunicationIdContext.getVisitor(visitorId);
        if (visitor != null) {
            // 上报关闭这个客户端的访客通道
            NettyProxyMsg closeVisitorMsg = new NettyProxyMsg();
            closeVisitorMsg.setType(MessageType.REPORT_SINGLE_CLIENT_CLOSE_VISITOR);
            closeVisitorMsg.setVisitorId(visitorId);
            visitor.writeAndFlush(closeVisitorMsg);
        }

        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
//        String vid = ctx.channel().attr(Constant.VID).get();
//        if (StringUtil.isNullOrEmpty(vid)) {
//            super.channelWritabilityChanged(ctx);
//            return;
//        }
//        Channel proxyChannel = Constant.vpc.get(vid);
//        if (proxyChannel != null) {
//            proxyChannel.config().setOption(ChannelOption.AUTO_READ, ctx.channel().isWritable());
//        }

        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}