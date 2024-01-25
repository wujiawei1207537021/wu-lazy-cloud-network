package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced;

@Slf4j
@Component
public class ClientHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced extends AbstractHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced<NettyProxyMsg> {
    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        // 获取访客ID
        byte[] visitorId = nettyProxyMsg.getVisitorId();
        // 获取访客对应的真实代理通道
        Channel realChannel = NettyRealIdContext.getReal(visitorId);
        if (realChannel != null) {
            realChannel.config().setOption(ChannelOption.AUTO_READ, true);
        }

    }
}
