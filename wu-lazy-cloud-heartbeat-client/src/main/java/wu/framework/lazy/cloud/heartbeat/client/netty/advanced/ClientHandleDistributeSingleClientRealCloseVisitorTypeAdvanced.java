package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.NettyCommunicationIdContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeSingleClientRealCloseVisitorTypeAdvanced;

@Slf4j
@Component
public class ClientHandleDistributeSingleClientRealCloseVisitorTypeAdvanced extends AbstractHandleDistributeSingleClientRealCloseVisitorTypeAdvanced<NettyProxyMsg> {
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
