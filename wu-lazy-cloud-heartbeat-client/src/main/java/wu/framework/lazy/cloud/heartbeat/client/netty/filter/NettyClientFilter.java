package wu.framework.lazy.cloud.heartbeat.client.netty.filter;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import wu.framework.lazy.cloud.heartbeat.client.netty.handler.NettyClientHandler;
import wu.framework.lazy.cloud.heartbeat.client.netty.socket.NettyClientSocket;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.decoder.NettyProxyMsgDecoder;
import wu.framework.lazy.cloud.heartbeat.common.encoder.NettyProxyMsgEncoder;

public class NettyClientFilter extends ChannelInitializer<SocketChannel> {


    private final ChannelTypeAdapter channelTypeAdapter;
    private final NettyClientSocket nettyClientSocket;

    public NettyClientFilter(ChannelTypeAdapter channelTypeAdapter, NettyClientSocket nettyClientSocket) {
        this.channelTypeAdapter = channelTypeAdapter;
        this.nettyClientSocket = nettyClientSocket;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /* * 解码和编码，应和服务端一致 * */
//        pipeline.addLast(new NettyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
//        pipeline.addLast(new NettMsgEncoder());
        // 解码、编码
        pipeline.addLast(new NettyProxyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
        pipeline.addLast(new NettyProxyMsgEncoder());
// pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式
        //因为服务端设置的超时时间是5秒，所以设置4秒

        pipeline.addLast(new IdleStateHandler(0, 4, 0));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("doHandler", new NettyClientHandler(channelTypeAdapter, nettyClientSocket)); //客户端的逻辑
    }
}