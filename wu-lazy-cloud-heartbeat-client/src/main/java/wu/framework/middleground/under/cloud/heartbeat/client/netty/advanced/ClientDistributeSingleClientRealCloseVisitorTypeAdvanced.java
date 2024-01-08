package wu.framework.middleground.under.cloud.heartbeat.client.netty.advanced;

import wu.framework.middleground.cloud.heartbeat.common.NettyCommunicationIdContext;
import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.middleground.cloud.heartbeat.common.advanced.client.AbstractDistributeSingleClientRealCloseVisitorTypeAdvanced;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientDistributeSingleClientRealCloseVisitorTypeAdvanced extends AbstractDistributeSingleClientRealCloseVisitorTypeAdvanced<NettyProxyMsg> {
    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        // 关闭代理的真实通道
        byte[] visitorId = nettyProxyMsg.getVisitorId();
        NettyRealIdContext.clear(visitorId);
        NettyCommunicationIdContext.clear(visitorId);

    }
}