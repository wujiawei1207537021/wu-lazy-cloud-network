package wu.framework.lazy.cloud.heartbeat.client.netty.handler;

import wu.framework.lazy.cloud.heartbeat.client.netty.socket.NettyClientSocket;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/09/13 10:29
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<NettyProxyMsg> {

    private final ChannelTypeAdapter channelTypeAdapter;
    private final NettyClientSocket nettyClientSocket;


    public NettyClientHandler(ChannelTypeAdapter channelTypeAdapter, NettyClientSocket nettyClientSocket) {
        this.channelTypeAdapter = channelTypeAdapter;
        this.nettyClientSocket = nettyClientSocket;
    }

    /**
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyProxyMsg msg) {
//        log.info("第" + count + "次" + ",客户端接受的消息:" + msg);
//        log.info("第" + count + "次" + ",客户端接受的消息内容:" + new String(msg.getData()));
//        count++;
        // 接收服务端、或者是代理端的信息
        Channel channel = ctx.channel();
//        log.info("type:{},clientId:{},data:{}",msg.getType(),new String(msg.getClientId()),new String(msg.getData()));
        channelTypeAdapter.handler(channel, msg);
    }

    /**
     * 建立连接时
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("建立连接时：" + new Date());
        ctx.fireChannelActive();
        String clientId = nettyClientSocket.getClientId();
        // 处理客户端连接成功
        Channel channel = ctx.channel();
        NettyProxyMsg nettyMsg = new NettyProxyMsg();
        nettyMsg.setType(MessageType.REPORT_CLIENT_CONNECT_SUCCESS);
        nettyMsg.setClientId(clientId);
        channelTypeAdapter.handler(channel, nettyMsg);


    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("关闭连接时：" + new Date());
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            try {
                nettyClientSocket.newConnect2Server();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1L, TimeUnit.SECONDS);

        super.channelInactive(ctx);
    }

    /**
     * 心跳请求处理 * 每4秒发送一次心跳请求; *
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.WRITER_IDLE.equals(event.state())) {  //如果写通道处于空闲状态,就发送心跳命令
                String clientId = nettyClientSocket.getClientId();
                NettyProxyMsg nettyMsg = new NettyProxyMsg();
                nettyMsg.setType(MessageType.TYPE_HEARTBEAT);
                nettyMsg.setData(clientId.getBytes(StandardCharsets.UTF_8));
                nettyMsg.setClientId(clientId.getBytes(StandardCharsets.UTF_8));
                ctx.writeAndFlush(nettyMsg);// 发送心跳数据
            } else if (event.state() == IdleState.WRITER_IDLE) { // 如果检测到写空闲状态，关闭连接
                // 离线、暂存通知
                String clientId = nettyClientSocket.getClientId();
                Channel channel = ctx.channel();
                NettyProxyMsg nettyMsg = new NettyProxyMsg();
                nettyMsg.setType(MessageType.DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION);
                nettyMsg.setClientId(clientId.getBytes(StandardCharsets.UTF_8));
                channelTypeAdapter.handler(channel, nettyMsg);
                ctx.close();
            }

        } else {
            super.userEventTriggered(ctx, obj);
        }
    }


}