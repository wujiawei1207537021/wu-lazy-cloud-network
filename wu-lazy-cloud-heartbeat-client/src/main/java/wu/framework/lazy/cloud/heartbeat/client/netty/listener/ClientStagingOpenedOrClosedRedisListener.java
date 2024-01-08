package wu.framework.lazy.cloud.heartbeat.client.netty.listener;


import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.client.rpc.StagingNoticeApiRpc;
import wu.framework.lazy.cloud.heartbeat.common.constant.RedisChannelConstant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 客户端暂存开启、关闭通知
 *
 * @see RedisChannelConstant#REDIS_CLIENT_STAGING_OPENED_OR_CLOSED_CHANNEL
 */
@Slf4j
@Component
public class ClientStagingOpenedOrClosedRedisListener {

    private final StagingNoticeApiRpc stagingNoticeApiRpc;
    private final NettyServerProperties nettyServerProperties;


    public ClientStagingOpenedOrClosedRedisListener(StagingNoticeApiRpc stagingNoticeApiRpc, NettyServerProperties nettyServerProperties) {
        this.stagingNoticeApiRpc = stagingNoticeApiRpc;
        this.nettyServerProperties = nettyServerProperties;

    }

//    /**
//     * 使用redis监听注解监听数据
//     *
//     * @param consumerRecord 客户端暂存开启、关闭通知
//     */
//    @EasyRedisListener(topics = RedisChannelConstant.REDIS_CLIENT_STAGING_OPENED_OR_CLOSED_CHANNEL)
//    public void subscription(ConsumerRecord<String, ClientStagingRedisChannelBo> consumerRecord, Acknowledgment acknowledgment) {
//        ClientStagingRedisChannelBo payload = consumerRecord.payload();
//        String clientId = payload.getClientId();
//        // 如果客户端ID为空默认当前客户端
//        if (ObjectUtils.isEmpty(clientId)) {
//            clientId = nettyServerProperties.getClientId();
//        }
//        StagingStatus stagingStatus = payload.getStagingStatus();
//        log.info("客户端:【{}】暂存:【{}】通知", clientId, stagingStatus);
//
//
//        if (StagingStatus.OPENED.equals(stagingStatus)) {
//            stagingNoticeApiRpc.stagingOpened(clientId);
//        } else if (StagingStatus.CLOSED.equals(stagingStatus)) {
//            stagingNoticeApiRpc.stagingClosed(clientId);
//        }
//        acknowledgment.acknowledge();
//    }
}
