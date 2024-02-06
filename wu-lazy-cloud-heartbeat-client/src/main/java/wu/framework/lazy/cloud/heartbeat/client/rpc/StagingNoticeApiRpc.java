package wu.framework.lazy.cloud.heartbeat.client.rpc;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyRpc;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.netty.channel.Channel;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;

import java.nio.charset.StandardCharsets;
import java.util.List;

@LazyRpc
public class StagingNoticeApiRpc {


    /**
     * 前提当前客户端曾经有过离线、而后上线了，但是呢现在有暂存的数据，因此暂存是打开的，所以这个时候可以通知服务端让其告知其他客户户我暂存了
     * 通知自己暂存了
     *
     * @param clientId 租户ID
     * @return
     */
    public Result<Void> stagingOpened(String clientId) {
        List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
        for (ChannelContext.ClientChannel clientChannel : clientChannels) {
            // 上报 当前通道暂存开启了
            Channel channel = clientChannel.getChannel();

            NettyProxyMsg nettyMsg = new NettyProxyMsg();
            nettyMsg.setType(MessageType.REPORT_CLIENT_STAGING_OPENED);
            nettyMsg.setData((clientId
                    .getBytes(StandardCharsets.UTF_8)));
            nettyMsg.setClientId((clientId
                    .getBytes(StandardCharsets.UTF_8)));
            channel.writeAndFlush(nettyMsg);
        }

        return ResultFactory.successOf();
    }

    /**
     * 暂存恢复
     *
     * @param clientId 租户ID
     * @return
     */
    public Result<Void> stagingClosed(String clientId) {
        List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
        for (ChannelContext.ClientChannel clientChannel : clientChannels) {
            // 上报 当前通道暂存关闭了
            Channel channel = clientChannel.getChannel();
            NettyProxyMsg nettyMsg = new NettyProxyMsg();
            nettyMsg.setType(MessageType.REPORT_CLIENT_STAGING_CLOSED);
            nettyMsg.setData((clientId
                    .getBytes(StandardCharsets.UTF_8)));
            nettyMsg.setClientId((clientId
                    .getBytes(StandardCharsets.UTF_8)));
            channel.writeAndFlush(nettyMsg);
        }

        return ResultFactory.successOf();
    }
}
