package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;


import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.NettyRealIdContext;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeChannelTransferTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;


/**
 * 服务端处理客户端数据传输
 *
 * @see MessageTypeEnums#DISTRIBUTE_CLIENT_TRANSFER
 */
@Slf4j
@Component
public class ClientReportHandleChannelTransferTypeAdvancedHandleDistribute extends AbstractHandleDistributeChannelTransferTypeAdvanced<NettyProxyMsg> {

    private final NettyServerProperties nettyServerProperties;

    public ClientReportHandleChannelTransferTypeAdvancedHandleDistribute(NettyServerProperties nettyServerProperties) {
        this.nettyServerProperties = nettyServerProperties;
    }

    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    public void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        log.debug("接收到服务端需要内网穿透的数据" + nettyProxyMsg);
        String clientId = nettyServerProperties.getClientId();
        byte[] visitorPort = nettyProxyMsg.getVisitorPort();
        byte[] clientTargetIp = nettyProxyMsg.getClientTargetIp();
        byte[] clientTargetPort = nettyProxyMsg.getClientTargetPort();
        byte[] visitorId = nettyProxyMsg.getVisitorId();
        // 真实服务通道
        Channel realChannel = NettyRealIdContext.getReal(new String(visitorId));
        if (realChannel == null) {
            log.error("无法获取访客:{} 真实服务", new String(visitorId));
            return;
        }


        // 把数据转到真实服务
        ByteBuf buf = channel.config().getAllocator().buffer(nettyProxyMsg.getData().length);
        buf.writeBytes(nettyProxyMsg.getData());

        realChannel.writeAndFlush(buf);

    }

}
