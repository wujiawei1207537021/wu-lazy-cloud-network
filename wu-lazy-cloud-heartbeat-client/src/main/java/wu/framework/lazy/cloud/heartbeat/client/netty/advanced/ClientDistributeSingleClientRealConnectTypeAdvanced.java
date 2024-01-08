package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.client.netty.socket.NettyClientRealSocket;
import wu.framework.lazy.cloud.heartbeat.common.InternalNetworkPenetrationRealClient;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractDistributeSingleClientRealConnectTypeAdvanced;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 客户端创建真实代理同奥
 */
@Slf4j
@Component
public class ClientDistributeSingleClientRealConnectTypeAdvanced extends AbstractDistributeSingleClientRealConnectTypeAdvanced<NettyProxyMsg> {

    private final NettyServerProperties nettyServerProperties;// 服务端地址信息
    private final List<ChannelTypeAdvanced> channelTypeAdvancedList;

    public ClientDistributeSingleClientRealConnectTypeAdvanced(NettyServerProperties nettyServerProperties, List<ChannelTypeAdvanced> channelTypeAdvancedList) {
        this.nettyServerProperties = nettyServerProperties;
        this.channelTypeAdvancedList = channelTypeAdvancedList;
    }

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg msg) {
        // 创建真实端口监听
        byte[] clientIdBytes = msg.getClientId();
        byte[] visitorPort = msg.getVisitorPort();
        byte[] clientTargetIp = msg.getClientTargetIp();
        byte[] clientTargetPort = msg.getClientTargetPort();
        byte[] visitorIdBytes = msg.getVisitorId();
        InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient = new InternalNetworkPenetrationRealClient();
        internalNetworkPenetrationRealClient.setClientId(new String(clientIdBytes));
        internalNetworkPenetrationRealClient.setVisitorPort(Integer.valueOf(new String(visitorPort)));
        internalNetworkPenetrationRealClient.setClientTargetIp(new String( clientTargetIp));
        internalNetworkPenetrationRealClient.setClientTargetPort(Integer.valueOf(new String( clientTargetPort)));
        String visitorId=new String(visitorIdBytes);// 访客ID
        internalNetworkPenetrationRealClient.setVisitorId(visitorId);

        // 绑定真实服务端口
        NettyClientRealSocket.buildRealServer(internalNetworkPenetrationRealClient,nettyServerProperties,channelTypeAdvancedList );

    }
}
