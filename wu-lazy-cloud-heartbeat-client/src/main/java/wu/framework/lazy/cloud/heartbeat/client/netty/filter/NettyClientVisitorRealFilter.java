package wu.framework.lazy.cloud.heartbeat.client.netty.filter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import wu.framework.lazy.cloud.heartbeat.client.netty.handler.NettyClientVisitorRealHandler;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.decoder.NettyProxyMsgDecoder;
import wu.framework.lazy.cloud.heartbeat.common.encoder.NettyProxyMsgEncoder;

/**
 * netty 客户端连接真实服服务端访客拦截器
 */
public class NettyClientVisitorRealFilter extends ChannelInitializer<SocketChannel> {
    private final ChannelTypeAdapter channelTypeAdapter;

    public NettyClientVisitorRealFilter(ChannelTypeAdapter channelTypeAdapter) {
        this.channelTypeAdapter = channelTypeAdapter;
    }

    /**
     * This method will be called once the {@link Channel} was registered. After the method returns this instance
     * will be removed from the {@link ChannelPipeline} of the {@link Channel}.
     *
     * @param ch the {@link Channel} which was registered.
     * @throws Exception is thrown if an error occurs. In that case it will be handled by
     *                   {@link #exceptionCaught(ChannelHandlerContext, Throwable)} which will by default close
     *                   the {@link Channel}.
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
//        // 解码、编码
//        pipeline.addLast(new NettyProxyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
//        pipeline.addLast(new NettMsgEncoder());
        pipeline.addLast(new NettyProxyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
        pipeline.addLast(new NettyProxyMsgEncoder());
        pipeline.addLast(new NettyClientVisitorRealHandler(channelTypeAdapter));
    }
}
