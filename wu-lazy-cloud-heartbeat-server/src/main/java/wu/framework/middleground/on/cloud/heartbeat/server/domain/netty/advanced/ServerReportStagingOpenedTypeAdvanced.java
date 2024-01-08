package wu.framework.middleground.on.cloud.heartbeat.server.domain.netty.advanced;

import wu.framework.middleground.cloud.heartbeat.common.ChannelContext;
import wu.framework.middleground.cloud.heartbeat.common.MessageType;
import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.advanced.server.AbstractReportStagingOpenedTypeAdvanced;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.application.ServerNettyConfigApplication;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 服务端处理上报的暂存开启
 */
@Slf4j
@Component
public class ServerReportStagingOpenedTypeAdvanced extends AbstractReportStagingOpenedTypeAdvanced<NettyProxyMsg> {

    private final ServerNettyConfigApplication serverNettyConfigApplication;

    public ServerReportStagingOpenedTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        this.serverNettyConfigApplication = serverNettyConfigApplication;
    }

    /**
     * 处理当前数据
     *
     * @param stagingOpenedChannel 开启暂存的通道
     * @param msg                  通道数据
     */
    @Override
    protected void doHandler(Channel stagingOpenedChannel, NettyProxyMsg msg) {
        // 获取所有通道
        byte[] clientIdBytes = msg.getClientId();
        List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
        ChannelId stagingOpenedChannelId = stagingOpenedChannel.id();
        ChannelContext.ClientChannel stagingOpenedClientChannel = ChannelContext.get(stagingOpenedChannelId);
        if (stagingOpenedClientChannel != null) {
            for (ChannelContext.ClientChannel clientChannel : clientChannels) {
                // 存储当前客户端暂存关闭
                String clientId = new String(clientIdBytes);
                serverNettyConfigApplication.stagingOpened(clientId);
                // 告诉他们 当前参数这个通道 暂存开启了
                Channel channel = clientChannel.getChannel();
                NettyProxyMsg nettyMsg = new NettyProxyMsg();
                nettyMsg.setType(MessageType.DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION);
                nettyMsg.setData((clientId
                        .getBytes(StandardCharsets.UTF_8)));
                nettyMsg.setClientId((clientId
                        .getBytes(StandardCharsets.UTF_8)));
                channel.writeAndFlush(nettyMsg);
            }
        }


    }
}