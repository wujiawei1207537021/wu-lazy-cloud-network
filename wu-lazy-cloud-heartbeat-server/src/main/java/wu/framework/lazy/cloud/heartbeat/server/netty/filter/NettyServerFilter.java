package wu.framework.lazy.cloud.heartbeat.server.netty.filter;

import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.decoder.NettyProxyMsgDecoder;
import wu.framework.lazy.cloud.heartbeat.common.encoder.NettyProxyMsgEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.server.netty.handler.NettyServerHandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author 吴佳伟
 * @date 2023/09/13 10:26
 */
@Component
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {
    private final List<ChannelTypeAdvanced> channelTypeAdvancedList;

    public NettyServerFilter( List<ChannelTypeAdvanced> channelTypeAdvancedList) {
        this.channelTypeAdvancedList = channelTypeAdvancedList;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 以("\n")为结尾分割的 解码器
        // 解码、编码
        pipeline.addLast(new NettyProxyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
        pipeline.addLast(new NettyProxyMsgEncoder());
//        ph.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 解码和编码，应和客户端一致
        //入参说明: 读超时时间、写超时时间、所有类型的超时时间、时间格式

        pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        // 类型处理器适配器
        ChannelTypeAdapter channelTypeAdapter = new ChannelTypeAdapter(channelTypeAdvancedList);
        pipeline.addLast("doHandler", new NettyServerHandler(channelTypeAdapter));// 服务端业务逻辑
    }
}