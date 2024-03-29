package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.NettyCommunicationIdContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.AbstractHandleReportSingleClientCloseVisitorTypeAdvanced;

/**
 * 服务端处理客户端 关闭一个访客
 */
@Slf4j
@Component
public class ServerHandleReportSingleClientCloseVisitorTypeAdvanced extends AbstractHandleReportSingleClientCloseVisitorTypeAdvanced<NettyProxyMsg> {


    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        // 关闭访客通道
        byte[] visitorId = nettyProxyMsg.getVisitorId();
        NettyCommunicationIdContext.clear(visitorId);
        NettyRealIdContext.clear(visitorId);
    }
}
