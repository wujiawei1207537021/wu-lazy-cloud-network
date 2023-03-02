package com.lazy.netty.proxy.server.handler;


import com.lazy.netty.proxy.msg.MyMsg;
import com.lazy.netty.proxy.server.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.StringUtil;

public class ClientHandler extends SimpleChannelInboundHandler<MyMsg> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyMsg myMsg) {
        // 代理服务器读到客户端数据了
        byte type = myMsg.getType();
        switch (type) {
            case MyMsg.TYPE_HEARTBEAT:
                MyMsg hb = new MyMsg();
                hb.setType(MyMsg.TYPE_HEARTBEAT);
                ctx.channel().writeAndFlush(hb);
                break;
            case MyMsg.TYPE_CONNECT:
                String vid = new String(myMsg.getData());
                if (StringUtil.isNullOrEmpty(vid) || "client".equals(vid)) {
                    Constant.clientChannel = ctx.channel();
                } else {
                    // 绑定访客和客户端的连接
                    Channel visitorChannel = Constant.vvc.get(vid);
                    if (null != visitorChannel) {
                        ctx.channel().attr(Constant.VID).set(vid);
                        Constant.vcc.put(vid, ctx.channel());

                        // 通道绑定完成可以读取访客数据
                        visitorChannel.config().setOption(ChannelOption.AUTO_READ, true);
                    }
                }
                break;
            case MyMsg.TYPE_DISCONNECT:
                String disVid = new String(myMsg.getData());
                Constant.clearVccVvcAndClose(disVid);
                break;
            case MyMsg.TYPE_TRANSFER:
                // 把数据转到用户服务
                ByteBuf buf = ctx.alloc().buffer(myMsg.getData().length);
                buf.writeBytes(myMsg.getData());

                String visitorId = ctx.channel().attr(Constant.VID).get();
                Channel vchannel = Constant.vvc.get(visitorId);
                if (null != vchannel) {
                    vchannel.writeAndFlush(buf);
                }
                break;
            default:
                // 操作有误
        }
        // 代理服务器发送数据到用户了
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        String vid = ctx.channel().attr(Constant.VID).get();
        if (StringUtil.isNullOrEmpty(vid)) {
            super.channelWritabilityChanged(ctx);
            return;
        }
        Channel visitorChannel = Constant.vvc.get(vid);
        if (visitorChannel != null) {
            visitorChannel.config().setOption(ChannelOption.AUTO_READ, ctx.channel().isWritable());
        }

        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String vid = ctx.channel().attr(Constant.VID).get();
        if (StringUtil.isNullOrEmpty(vid)) {
            super.channelInactive(ctx);
            return;
        }
        Channel visitorChannel = Constant.vvc.get(vid);
        if (visitorChannel != null && visitorChannel.isActive()) {
            // 数据发送完成后再关闭连接，解决http1.0数据传输问题
            visitorChannel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            visitorChannel.close();
        } else {
            ctx.channel().close();
        }
        Constant.clearVccVvc(vid);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    ctx.channel().close();
                    break;
                case WRITER_IDLE:
                    break;
                case ALL_IDLE:
                    break;
            }
        }
    }
}