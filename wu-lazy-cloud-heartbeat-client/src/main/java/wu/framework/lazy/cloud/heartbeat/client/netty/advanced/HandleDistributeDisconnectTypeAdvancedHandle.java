package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;


import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeDisconnectTypeAdvancedHandle;
import wu.framework.lazy.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


/**
 * 服务端处理客户端断开连接处理
 * TYPE_DISCONNECT
 */
@Slf4j
@Component
public class HandleDistributeDisconnectTypeAdvancedHandle extends AbstractHandleDistributeDisconnectTypeAdvancedHandle<NettyProxyMsg> {


    private final ClientNettyConfigApplication clientNettyConfigApplication;

    public HandleDistributeDisconnectTypeAdvancedHandle(ClientNettyConfigApplication clientNettyConfigApplication) {
        this.clientNettyConfigApplication = clientNettyConfigApplication;
    }

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    public void doHandler(Channel channel, NettyProxyMsg msg) {
        // 服务下线
        byte[] data = msg.getData();
        byte[] clientId = msg.getClientId();
        String tenantId = new String(clientId);
        log.warn("客户端:{}下线",tenantId);
        clientNettyConfigApplication.clientOffLine(tenantId);

    }

}
