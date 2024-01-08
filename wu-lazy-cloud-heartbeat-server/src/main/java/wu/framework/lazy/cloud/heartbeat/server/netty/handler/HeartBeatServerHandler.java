package wu.framework.lazy.on.cloud.heartbeat.server.domain.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

@Deprecated
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {

    AttributeKey<String> channelAttributeKeyId = AttributeKey.valueOf("channelAttributeTenantId");
    private int lossConnectCount = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        String channelAttributeId = ctx.channel().attr(channelAttributeKeyId).get();
        System.out.println("channelAttributeTenantId:" + channelAttributeId);
        System.out.println("已经5秒未收到客户端的消息了！");

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                lossConnectCount++;
                if (lossConnectCount > 2) {
                    System.out.println("关闭这个不活跃通道！");
//                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        lossConnectCount = 0;
        System.out.println("client says: " + msg.toString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}