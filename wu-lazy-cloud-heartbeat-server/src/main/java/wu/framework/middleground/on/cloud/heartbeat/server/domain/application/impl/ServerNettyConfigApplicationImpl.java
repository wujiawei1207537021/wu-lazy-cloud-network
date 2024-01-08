package wu.framework.middleground.on.cloud.heartbeat.server.domain.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import wu.framework.lazy.cloud.heartbeat.common.constant.ClientConfigKeyUtils;
import wu.framework.lazy.cloud.heartbeat.common.enums.NettyClientStatus;

import wu.framework.middleground.on.cloud.heartbeat.server.domain.application.ServerNettyConfigApplication;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.model.netty.client.state.NettyClientState;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.model.netty.client.state.NettyClientStateRepository;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@LazyApplication
public class ServerNettyConfigApplicationImpl implements ServerNettyConfigApplication {

    private final NettyClientStateRepository nettyClientStateRepository;


    public ServerNettyConfigApplicationImpl(NettyClientStateRepository nettyClientStateRepository) {

        this.nettyClientStateRepository = nettyClientStateRepository;
    }


    /**
     * 客户端在线
     *
     * @param clientId 客户端ID
     */
    @Override
    public void clientOnLine(String clientId) {
        // 如果可以已经在线状态不推送
        String clientStatusKey = ClientConfigKeyUtils.getClientStatusKey(clientId);
//        stringRedisTemplate.opsForValue().set(clientStatusKey, NettyClientStatus.ON_LINE.name());
        NettyClientState nettyClientState = new NettyClientState();
        nettyClientState.setClientId(clientId);
        nettyClientState.setOnLineState(NettyClientStatus.ON_LINE);
        nettyClientStateRepository.updateOnLIneState(nettyClientState);
        // 触发暂存扫描
//        ClientOnLineState clientOnLineState = new ClientOnLineState();
//        clientOnLineState.setClientId(clientId);
//        clientOnLineState.setOnLineState(NettyClientStatus.ON_LINE.name());
//        stringRedisTemplate.convertAndSend(REDIS_CLIENT_ONLINE_OR_OFFLINE_CHANNEL,clientOnLineState);
    }

    /**
     * 客户端离线
     *
     * @param clientId 客户端ID
     */
    @Override
    public void clientOffLine(String clientId) {
        // 如果可以已经在线状态不推送
        String clientStatusKey = ClientConfigKeyUtils.getClientStatusKey(clientId);
//        stringRedisTemplate.opsForValue().set(clientStatusKey, NettyClientStatus.OFF_LINE.name());
        // 修改客户端状态 离线
        NettyClientState nettyClientState = new NettyClientState();
        nettyClientState.setClientId(clientId);
        nettyClientState.setOnLineState(NettyClientStatus.OFF_LINE);
        nettyClientState.setStagingState("OPENED");
        nettyClientStateRepository.updateOnLIneState(nettyClientState);
//        // 触发暂存扫描
//        ClientOnLineState clientOnLineState = new ClientOnLineState();
//        clientOnLineState.setClientId(clientId);
//        clientOnLineState.setOnLineState(NettyClientStatus.OFF_LINE.name());
//        stringRedisTemplate.convertAndSend(REDIS_CLIENT_ONLINE_OR_OFFLINE_CHANNEL,clientOnLineState);

    }

    /**
     * 客户端暂存关闭
     *
     * @param clientId 客户端ID
     */
    @Override
    public void stagingClosed(String clientId) {
        NettyClientState nettyClientState = new NettyClientState();
        nettyClientState.setClientId(clientId);
        nettyClientState.setStagingState("CLOSED");
        nettyClientStateRepository.updateStagingState(nettyClientState);
    }

    /**
     * 客户端暂存开启
     *
     * @param clientId 客户端ID
     */
    @Override
    public void stagingOpened(String clientId) {
        NettyClientState nettyClientState = new NettyClientState();
        nettyClientState.setClientId(clientId);
        nettyClientState.setStagingState("OPENED");
        nettyClientStateRepository.updateStagingState(nettyClientState);
    }
}
