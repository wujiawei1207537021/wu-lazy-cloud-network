package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;


import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.AbstractHandleReportDisconnectTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.server.application.ServerNettyConfigApplication;

import java.util.List;


/**
 * 服务端处理客户端断开连接处理
 * TYPE_DISCONNECT
 */
@Slf4j
@Component
public class ServerHandleReportDisconnectTypeAdvanced extends AbstractHandleReportDisconnectTypeAdvanced<NettyProxyMsg> {
    private final ServerNettyConfigApplication serverNettyConfigApplication;

    public ServerHandleReportDisconnectTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        this.serverNettyConfigApplication = serverNettyConfigApplication;
    }

    /**
     * 处理当前数据
     *
     * @param deathChannel 关闭的通道
     * @param msg          通道数据
     */
    @Override
    public void doHandler(Channel deathChannel, NettyProxyMsg msg) {
        // 关闭连接通知
        byte[] clientIdByte = msg.getClientId();
        log.info("关闭客户端:{} 的通道", new String(clientIdByte));
        ChannelId deathChannelId = deathChannel.id();
        ChannelContext.ClientChannel deathClientChannelDTO = ChannelContext.get(clientIdByte);

        if (deathClientChannelDTO != null) {
            byte[] clientId = deathClientChannelDTO.getClientId();
            // 服务状态离线
            String tenantId = new String(clientId);
            serverNettyConfigApplication.clientOffLine(tenantId);
            ChannelContext.remove(clientIdByte);
            List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();

            // 通知其他客户端 channelId 关闭了
            for (ChannelContext.ClientChannel clientChannel : clientChannels) {
                Channel channel = clientChannel.getChannel();

                // 离线通知
                NettyProxyMsg nettyMsg = new NettyProxyMsg();
                nettyMsg.setType(MessageType.DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION);
                nettyMsg.setClientId(clientId);
                nettyMsg.setData(clientId);
                channel.writeAndFlush(nettyMsg);
                // 暂存通知
                NettyProxyMsg stagingNettyProxyMsg = new NettyProxyMsg();
                stagingNettyProxyMsg.setType(MessageType.DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION);
                stagingNettyProxyMsg.setData(clientId);
                stagingNettyProxyMsg.setClientId(clientId);
                channel.writeAndFlush(stagingNettyProxyMsg);
            }
        }
    }


}
