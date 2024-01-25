package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.NettyCommunicationIdContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.AbstractHandleReportSingleClientRealConnectTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;

@Slf4j
@Component
public class ServerHandleReportSingleClientRealConnectTypeAdvanced extends AbstractHandleReportSingleClientRealConnectTypeAdvanced<NettyProxyMsg> {

    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        // 客户端绑定端口成功
        byte[] clientId = nettyProxyMsg.getClientId();
        byte[] clientTargetIp = nettyProxyMsg.getClientTargetIp();
        byte[] clientTargetPort = nettyProxyMsg.getClientTargetPort();
        byte[] visitorPort = nettyProxyMsg.getVisitorPort();
        byte[] visitorId = nettyProxyMsg.getVisitorId();
        log.info("客户端:{},绑定真实服务ip:{},port:{},成功", new String(clientId), new String(clientTargetIp), new String(clientTargetPort));
        // 绑定服务端访客通信通道
        NettyCommunicationIdContext.pushVisitor(channel, new String(visitorId));
        ChannelAttributeKeyUtils.buildVisitorId(channel, visitorId);
        ChannelAttributeKeyUtils.buildClientId(channel, clientId);
        ChannelAttributeKeyUtils.buildVisitorPort(channel, Integer.valueOf(new String(visitorPort)));
        // 访客通道开启自动读取
        Channel visitorRealChannel = NettyRealIdContext.getReal(new String(visitorId));
        visitorRealChannel.config().setOption(ChannelOption.AUTO_READ, true);

        // 或许此处还应该通知服务端 这个访客绑定的客户端真实通道打开

        // 下发客户端 真实通道自动读写开启


    }
}
