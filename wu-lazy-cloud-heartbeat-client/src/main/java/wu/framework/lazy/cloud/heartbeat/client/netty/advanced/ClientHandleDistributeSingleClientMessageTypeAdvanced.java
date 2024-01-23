package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeSingleClientMessageTypeAdvanced;

/**
 * 接收服务端发送过来的聊天信息
 */
@Slf4j
@Component
public class ClientHandleDistributeSingleClientMessageTypeAdvanced extends AbstractHandleDistributeSingleClientMessageTypeAdvanced<NettyProxyMsg> {
    /**
     * 处理当前数据
     *
     * @param channel       当前通道
     * @param nettyProxyMsg 通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg nettyProxyMsg) {
        byte[] clientId = nettyProxyMsg.getClientId();
        byte[] data = nettyProxyMsg.getData();
        log.info("接收客户端：{},发送过来的聊天信息:{}",new String( clientId),new String( data ));

    }
}
