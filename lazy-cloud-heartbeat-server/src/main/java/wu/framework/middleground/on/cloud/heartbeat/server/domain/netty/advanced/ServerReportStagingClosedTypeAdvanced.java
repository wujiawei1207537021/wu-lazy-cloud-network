
package wu.framework.middleground.on.cloud.heartbeat.server.domain.netty.advanced;

import wu.framework.middleground.cloud.heartbeat.common.ChannelContext;
import wu.framework.middleground.cloud.heartbeat.common.MessageType;
import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.advanced.server.AbstractReportStagingClosedTypeAdvanced;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.application.ServerNettyConfigApplication;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 服务端处理上报的暂存关闭
 */
@Slf4j
@Component
public class ServerReportStagingClosedTypeAdvanced extends AbstractReportStagingClosedTypeAdvanced <NettyProxyMsg>{
    private final ServerNettyConfigApplication serverNettyConfigApplication;

    public ServerReportStagingClosedTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        this.serverNettyConfigApplication = serverNettyConfigApplication;
    }

    /**
     * 处理当前数据
     *
     * @param stagingClosedChannel 关闭暂存的通道
     * @param msg                  通道数据
     */
    @Override
    protected void doHandler(Channel stagingClosedChannel, NettyProxyMsg msg) {
        byte[] clientIdBytes = msg.getClientId();
        // 获取所有通道
        List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
        ChannelId stagingClosedChannelId = stagingClosedChannel.id();
        ChannelContext.ClientChannel stagingOpenedClientChannel = ChannelContext.get(stagingClosedChannelId);
        if (stagingOpenedClientChannel != null) {
            String clientId = new String(clientIdBytes);
            // 存储当前客户端暂存关闭
            serverNettyConfigApplication.stagingClosed(clientId);
            for (ChannelContext.ClientChannel clientChannel : clientChannels) {
                // 告诉他们 当前参数这个通道 暂存关闭了
                Channel channel = clientChannel.getChannel();
                NettyProxyMsg nettyMsg = new NettyProxyMsg();
                nettyMsg.setType(MessageType.DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION);
                nettyMsg.setData((clientId
                        .getBytes(StandardCharsets.UTF_8)));
                nettyMsg.setClientId((clientId
                        .getBytes(StandardCharsets.UTF_8)));
                channel.writeAndFlush(nettyMsg);
            }
        }

    }
}
