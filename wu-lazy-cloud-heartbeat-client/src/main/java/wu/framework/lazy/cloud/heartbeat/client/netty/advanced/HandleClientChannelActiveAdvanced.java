package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import io.netty.channel.Channel;
import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleClientChannelActiveAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;

/**
 * 客户端通道 is active
 */
public class HandleClientChannelActiveAdvanced extends AbstractHandleClientChannelActiveAdvanced<NettyProxyMsg> {
    private final NettyServerProperties nettyServerProperties;

    public HandleClientChannelActiveAdvanced(NettyServerProperties nettyServerProperties) {
        this.nettyServerProperties = nettyServerProperties;
    }

    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        // 缓存当前通道
        String clientId = nettyServerProperties.getClientId();
        ChannelContext.push(channel, clientId);
        ChannelAttributeKeyUtils.buildClientId(channel, clientId);
    }
}
