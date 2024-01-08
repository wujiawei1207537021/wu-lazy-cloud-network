package wu.framework.middleground.under.cloud.heartbeat.client.application.impl;


import wu.framework.middleground.under.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@LazyApplication
public class ClientNettyConfigApplicationImpl implements ClientNettyConfigApplication {
//    private final StringRedisTemplate stringRedisTemplate;
//    private final RedisProviderTemplate redisProviderTemplate;
//
//    public ClientNettyConfigApplicationImpl(StringRedisTemplate stringRedisTemplate, RedisProviderTemplate redisProviderTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//        this.redisProviderTemplate = redisProviderTemplate;
//    }


    /**
     * 推送客户端在线
     */
    @Override
    public void clientOnLine(String clientId) {
//        // 获取当前客户端ID
//        if (ObjectUtils.isEmpty(clientId)) {
//            clientId = stringRedisTemplate.opsForValue().get(ClientConfigKeyUtils.CLIENT_ID_KEY);
//        }
//        String clientStatusKey = ClientConfigKeyUtils.getClientStatusKey(clientId);
//        // 如果可以已经在线状态不推送
//        stringRedisTemplate.opsForValue().set(clientStatusKey, NettyClientStatus.ON_LINE.name());
//        ClientOnLineState clientOnLineState = new ClientOnLineState();
//        clientOnLineState.setClientId(clientId);
//        clientOnLineState.setOnLineState(NettyClientStatus.ON_LINE.name());
//        // 暂存扫描触发
//        redisProviderTemplate.send(RedisChannelConstant.REDIS_CLIENT_ONLINE_OR_OFFLINE_CHANNEL,clientOnLineState);

    }

    /**
     * 推送客户端离线
     */
    @Override
    public void clientOffLine(String clientId) {
//        if (ObjectUtils.isEmpty(clientId)) {
//            clientId = stringRedisTemplate.opsForValue().get(ClientConfigKeyUtils.CLIENT_ID_KEY);
//        }
//        String clientStatusKey = ClientConfigKeyUtils.getClientStatusKey(clientId);
//        // 离线状态
//        stringRedisTemplate.opsForValue().set(clientStatusKey, NettyClientStatus.OFF_LINE.name());
//        // 暂存状态
//        stagingOpen(clientId);
//        // 暂存扫描触发
//        ClientOnLineState clientOnLineState = new ClientOnLineState();
//        clientOnLineState.setClientId(clientId);
//        clientOnLineState.setOnLineState(NettyClientStatus.OFF_LINE.name());
//        redisProviderTemplate.send(RedisChannelConstant.REDIS_CLIENT_ONLINE_OR_OFFLINE_CHANNEL,clientOnLineState);
    }

    @Override
    public void stagingOpen(String clientId) {
//        String stagingStatusKey = StagingConfigKeyConstant.getStagingStatusKey(clientId);
//        stringRedisTemplate.opsForValue().set(stagingStatusKey, StagingStatus.OPENED.name());

    }

    /**
     * 暂存关闭
     *
     * @param clientId 租户ID
     */
    @Override
    public void stagingClose(String clientId) {
//        if (clientId == null) {
//            clientId = stringRedisTemplate.opsForValue().get(ClientConfigKeyUtils.CLIENT_ID_KEY);
//        }
//        String stagingStatusKey = StagingConfigKeyConstant.getStagingStatusKey(clientId);
//        stringRedisTemplate.opsForValue().set(stagingStatusKey, StagingStatus.CLOSED.name());

    }

}
