package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.client.netty.socket.NettyClientRealSocket;
import wu.framework.lazy.cloud.heartbeat.common.InternalNetworkPenetrationRealClient;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeSingleClientRealConnectTypeAdvanced;

import java.util.List;

/**
 * 客户端创建真实代理同奥
 */
@Slf4j
@Component
public class ClientHandleDistributeSingleClientRealConnectTypeAdvanced extends AbstractHandleDistributeSingleClientRealConnectTypeAdvanced<NettyProxyMsg> {

    private final NettyServerProperties nettyServerProperties;// 服务端地址信息
    private final List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList;

    public ClientHandleDistributeSingleClientRealConnectTypeAdvanced(NettyServerProperties nettyServerProperties, List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) {
        this.nettyServerProperties = nettyServerProperties;
        this.handleChannelTypeAdvancedList = handleChannelTypeAdvancedList;
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
        InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient =
                InternalNetworkPenetrationRealClient
                        .builder()
                        .clientId(new String(clientIdBytes))
                        .visitorPort(Integer.valueOf(new String(visitorPort)))
                        .clientTargetIp(new String(clientTargetIp))
                        .clientTargetPort(Integer.valueOf(new String(clientTargetPort)))
                        .visitorId(new String(visitorIdBytes))
                        .build();

        // 绑定真实服务端口
        NettyClientRealSocket.buildRealServer(internalNetworkPenetrationRealClient, nettyServerProperties, handleChannelTypeAdvancedList);

    }
}
