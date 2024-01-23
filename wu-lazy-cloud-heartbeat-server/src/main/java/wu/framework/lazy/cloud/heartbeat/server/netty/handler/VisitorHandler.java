package wu.framework.lazy.cloud.heartbeat.server.netty.handler;


import wu.framework.lazy.cloud.heartbeat.common.*;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class VisitorHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient;

    public VisitorHandler(InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient) {
        this.internalNetworkPenetrationRealClient = internalNetworkPenetrationRealClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 访客连接上代理服务器了
        Channel visitorChannel = ctx.channel();
        // 先不读取访客数据
        visitorChannel.config().setOption(ChannelOption.AUTO_READ, false);


        // 生成访客ID
        String visitorId = UUID.randomUUID().toString();
        String clientId = internalNetworkPenetrationRealClient.getClientId();
        Integer visitorPort = internalNetworkPenetrationRealClient.getVisitorPort();
        String clientTargetIp = internalNetworkPenetrationRealClient.getClientTargetIp();
        Integer clientTargetPort = internalNetworkPenetrationRealClient.getClientTargetPort();
        // 绑定访客真实通道
        NettyRealIdContext.pushReal(visitorChannel, visitorId);
        // 当前通道绑定访客ID
        ChannelAttributeKeyUtils.buildVisitorId(visitorChannel, visitorId);
        ChannelAttributeKeyUtils.buildClientId(visitorChannel, clientId);
        NettyProxyMsg nettyProxyMsg = new NettyProxyMsg();
        nettyProxyMsg.setType(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT);
        nettyProxyMsg.setClientId(clientId);
        nettyProxyMsg.setVisitorPort(visitorPort);
        nettyProxyMsg.setClientTargetIp(clientTargetIp);
        nettyProxyMsg.setClientTargetPort(clientTargetPort);

        nettyProxyMsg.setVisitorId(visitorId);

        // 客户端心跳通道
        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId);
        if (clientChannel != null) {
            log.info("通过客户端:{},获取通道而后创建连接",clientId);
            Channel channel = clientChannel.getChannel();
            channel.writeAndFlush(nettyProxyMsg);
        }else {
            log.error("无法通过客户端ID获取客户端通道");
        }


        // 等待访客ID传输到客户端后绑定客户端真实服务后开启


        log.info("服务端访客端口连接成功了");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) {

        String clientId = internalNetworkPenetrationRealClient.getClientId();
        String clientTargetIp = internalNetworkPenetrationRealClient.getClientTargetIp();
        Integer clientTargetPort = internalNetworkPenetrationRealClient.getClientTargetPort();
        Integer visitorPort = internalNetworkPenetrationRealClient.getVisitorPort();
        String visitorId = ChannelAttributeKeyUtils.getVisitorId(ctx.channel());
        if (StringUtil.isNullOrEmpty(clientId)) {
            return;
        }
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        // 获取客户端通道，而后进行数据下发
        log.debug("服务端访客端口成功接收数据:{}", new String(bytes));
        // 使用访客的通信通道
        Channel visitorCommunicationChannel = NettyCommunicationIdContext.getVisitor(visitorId);

        NettyProxyMsg nettyProxyMsg = new NettyProxyMsg();
        nettyProxyMsg.setType(MessageType.DISTRIBUTE_CLIENT_TRANSFER);
        nettyProxyMsg.setClientId(clientId);
        nettyProxyMsg.setClientTargetIp(clientTargetIp);
        nettyProxyMsg.setClientTargetPort(clientTargetPort);
        nettyProxyMsg.setVisitorPort(visitorPort);
        nettyProxyMsg.setVisitorId(visitorId);
        nettyProxyMsg.setData(bytes);
        visitorCommunicationChannel.writeAndFlush(nettyProxyMsg);
        log.debug("服务端访客端口成功发送数据了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String visitorId = ChannelAttributeKeyUtils.getVisitorId(ctx.channel());
        String clientId = ChannelAttributeKeyUtils.getClientId(ctx.channel());
        if (StringUtil.isNullOrEmpty(visitorId)) {
            super.channelInactive(ctx);
            return;
        }
        // 通信通道自动读写打开 ，然后关闭通信通道
        Channel visitorChannel = NettyCommunicationIdContext.getVisitor(visitorId);
        if (visitorChannel != null && visitorChannel.isActive()) {

            visitorChannel.config().setOption(ChannelOption.AUTO_READ, true);

            //  通知服务端 关闭访问通道、真实通道
            NettyProxyMsg myMsg = new NettyProxyMsg();
            myMsg.setType(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR);
            myMsg.setVisitorId(visitorId);
            visitorChannel.writeAndFlush(myMsg);
        }
        // 关闭 访客通信通道、访客真实通道
        NettyRealIdContext.clear(visitorId);
        NettyCommunicationIdContext.clear(visitorId);
        log.warn("服务端访客端口断开连接");
        super.channelInactive(ctx);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {

//        Channel visitorChannel = ctx.channel();
//        String vid = visitorChannel.attr(Constant.VID).get();
//        if (StringUtil.isNullOrEmpty(vid)) {
//            super.channelWritabilityChanged(ctx);
//            return;
//        }
//        Channel clientChannel = Constant.vcc.get(vid);
//        if (clientChannel != null) {
//            clientChannel.config().setOption(ChannelOption.AUTO_READ, visitorChannel.isWritable());
//        }
        log.info("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught");

        Channel channel = ctx.channel();
        String clientId = ChannelAttributeKeyUtils.getClientId(channel);
        String visitorId = ChannelAttributeKeyUtils.getVisitorId(channel);
        // 使用通信通道 下发关闭访客
        Channel visitorChannel = NettyCommunicationIdContext.getVisitor(visitorId);
        if (visitorChannel != null) {
            // 下发关闭访客
            NettyProxyMsg closeRealClient = new NettyProxyMsg();
            closeRealClient.setType(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ);
            closeRealClient.setClientId(clientId);
            closeRealClient.setVisitorId(visitorId);
            visitorChannel.writeAndFlush(closeRealClient);
        }

        ctx.close();
    }
}