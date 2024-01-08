package wu.framework.middleground.under.cloud.heartbeat.client.netty.filter;

import wu.framework.middleground.under.cloud.heartbeat.client.netty.handler.NettyClientRealHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyClientRealFilter  extends ChannelInitializer<SocketChannel> {
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
        pipeline.addLast(new NettyClientRealHandler());
//        // 解码、编码
//        pipeline.addLast(new NettyProxyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
//        pipeline.addLast(new NettMsgEncoder());
//        pipeline.addLast(new NettyProxyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
//        pipeline.addLast(new NettyProxyMsgEncoder());
    }
}
