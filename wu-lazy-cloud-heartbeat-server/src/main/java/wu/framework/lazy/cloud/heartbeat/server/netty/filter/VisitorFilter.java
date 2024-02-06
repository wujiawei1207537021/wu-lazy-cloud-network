package wu.framework.lazy.cloud.heartbeat.server.netty.filter;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import wu.framework.lazy.cloud.heartbeat.common.InternalNetworkPenetrationRealClient;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelFlowAdapter;
import wu.framework.lazy.cloud.heartbeat.server.netty.handler.VisitorHandler;

public class VisitorFilter extends ChannelInitializer<SocketChannel> {
    private final InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient;
    private final ChannelFlowAdapter channelFlowAdapter;

    public VisitorFilter(InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient, ChannelFlowAdapter channelFlowAdapter) {
        this.internalNetworkPenetrationRealClient = internalNetworkPenetrationRealClient;
        this.channelFlowAdapter = channelFlowAdapter;
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
        pipeline.addLast(new ChannelDuplexHandler());
        pipeline.addLast(new VisitorHandler(internalNetworkPenetrationRealClient, channelFlowAdapter));

    }
}
