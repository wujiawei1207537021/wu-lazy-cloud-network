package wu.framework.lazy.cloud.heartbeat.client.netty.handler;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;

/**
 * 客户端访客通信通道 处理器
 */
@Slf4j
public class NettyClientVisitorRealHandler extends SimpleChannelInboundHandler<NettyProxyMsg> {
    private final ChannelTypeAdapter channelTypeAdapter;

    public NettyClientVisitorRealHandler(ChannelTypeAdapter channelTypeAdapter) {
        this.channelTypeAdapter = channelTypeAdapter;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, NettyProxyMsg nettyProxyMsg) throws Exception {
        Channel channel = ctx.channel();
        channelTypeAdapter.handler(channel, nettyProxyMsg);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String clientId = ChannelAttributeKeyUtils.getClientId(ctx.channel());
        String visitorId = ChannelAttributeKeyUtils.getVisitorId(ctx.channel());
        // 关闭访客
        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId);
        if (clientChannel != null) {
            Channel channel = clientChannel.getChannel();
            // 上报关闭这个客户端的访客通道
            NettyProxyMsg closeVisitorMsg = new NettyProxyMsg();
            closeVisitorMsg.setType(MessageType.REPORT_SINGLE_CLIENT_CLOSE_VISITOR);
            closeVisitorMsg.setVisitorId(visitorId);
            channel.writeAndFlush(closeVisitorMsg);
        }

        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}