package wu.framework.middleground.on.cloud.heartbeat.server.domain.netty.handler;

import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyCommunicationIdContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

/**
 * description 服务端数据处理器
 *
 * @author 吴佳伟
 * @date 2023/09/13 10:27
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<NettyProxyMsg> {

    private final ChannelTypeAdapter channelTypeAdapter;

    public NettyServerHandler(ChannelTypeAdapter channelTypeAdapter) {
        this.channelTypeAdapter = channelTypeAdapter;
    }

    /**
     * Is called for each message of type {@link I}.
     *
     * @param ctx      the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *                 belongs to
     * @param nettyMsg the message to handle
     * @throws Exception is thrown if an error occurred
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyProxyMsg nettyMsg) throws Exception {
        // 客户端读取到代理过来的数据了
        Channel channel = ctx.channel();
        byte type = nettyMsg.getType();
//        byte[] data = nettyMsg.getData();
//        log.info("客户端发送数据类型:{},发送数据:{}", type, new String(data));
        channelTypeAdapter.handler(channel, nettyMsg);

    }

    /**
     * 空闲次数
     */
    private int idle_count = 1;

    /**
     * 超时处理 * 如果5秒没有接受客户端的心跳，就触发; * 如果超过两次，则直接关闭;
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
        Channel channel = ctx.channel();
        if (obj instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) obj;
            if (IdleState.READER_IDLE.equals(event.state())) {  //如果读通道处于空闲状态，说明没有接收到心跳命令
                String clientId = ChannelAttributeKeyUtils.getClientId(channel);
                String visitorId = ChannelAttributeKeyUtils.getVisitorId(channel);
                log.warn("已经5秒没有接收到客户端：{}的信息了",clientId);
                if (idle_count > 2) {

                    if(ObjectUtils.isEmpty(visitorId)){
                        log.warn("关闭这个不活跃的channel client:{}", clientId);
                        // 给所有客户端发送 这个客户端离线了
                        NettyProxyMsg nettyMsg = new NettyProxyMsg();
                        nettyMsg.setClientId(clientId);
                        nettyMsg.setVisitorId(visitorId);
                        nettyMsg.setType(MessageType.REPORT_CLIENT_DISCONNECTION);
                        channelTypeAdapter.handler(channel, nettyMsg);
                        channel.close();
                    }else {
                        log.info("关闭访客：【{}】的连接",visitorId);
                        NettyCommunicationIdContext.clear(visitorId);
                        NettyRealIdContext.clear(visitorId);
                    }

                }
                idle_count++;
            }
        } else {
            super.userEventTriggered(ctx, obj);
        }
    }


    /**
     * Calls {@link ChannelHandlerContext#fireChannelInactive()} to forward
     * to the next {@link ChannelInboundHandler} in the {@link ChannelPipeline}.
     * <p>
     * Sub-classes may override this method to change behavior.
     *
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        boolean open = channel.isOpen();
        // 下发当前客户端通道断开连接

        String clientId = ChannelAttributeKeyUtils.getClientId(channel);
        log.info("断开客户端的连接:{}", clientId);
        NettyProxyMsg nettyMsg = new NettyProxyMsg();
        nettyMsg.setType(MessageType.REPORT_CLIENT_DISCONNECTION);
        nettyMsg.setClientId(clientId);
        channelTypeAdapter.handler(channel, nettyMsg);
        super.channelInactive(ctx);
    }
}