package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeStagingClosedTypeAdvanced;

/**
 * 服务端下发暂存关闭消息处理
 */
@Slf4j
@Component
public class HandleDistributeStagingClosedTypeAdvanced extends AbstractHandleDistributeStagingClosedTypeAdvanced<NettyProxyMsg> {


    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg msg) {
        String clientId = new String(msg.getClientId());
        log.info("客户端：{}离线暂存关闭", clientId);
        // 修改redis 客户端暂存状态
//        String stagingStatusKey = StagingConfigKeyConstant.getStagingStatusKey(clientId);
//        stringRedisTemplate.opsForValue().set(stagingStatusKey, StagingStatus.CLOSED.name());

    }
}
