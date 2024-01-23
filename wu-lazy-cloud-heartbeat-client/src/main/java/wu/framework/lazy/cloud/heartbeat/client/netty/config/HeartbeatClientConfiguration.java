package wu.framework.lazy.cloud.heartbeat.client.netty.config;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import wu.framework.lazy.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import wu.framework.lazy.cloud.heartbeat.client.netty.advanced.*;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;

import java.util.List;
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class HeartbeatClientConfiguration {

    /**
     * 服务端 处理客户端心跳
     *
     * @return ClientHandleChannelHeartbeatTypeAdvanced
     */
    @Bean
    public ClientHandleChannelHeartbeatTypeAdvanced clientChannelHeartbeatTypeAdvanced() {
        return new ClientHandleChannelHeartbeatTypeAdvanced();
    }

    /**
     * 处理 客户端代理的真实端口自动读写
     * @return ClientHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced
     */
    @Bean
    public ClientHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced handleDistributeSingleClientRealAutoReadConnectTypeAdvanced(){
        return new ClientHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced();
    }
    /**
     * 处理 接收服务端发送过来的聊天信息
     * @return ClientHandleDistributeSingleClientMessageTypeAdvanced
     */
    @Bean
    public ClientHandleDistributeSingleClientMessageTypeAdvanced handleDistributeSingleClientMessageTypeAdvanced(){
        return new ClientHandleDistributeSingleClientMessageTypeAdvanced();
    }
    @Bean
    public ClientHandleDistributeSingleClientRealCloseVisitorTypeAdvanced handleDistributeSingleClientRealCloseVisitorTypeAdvanced(){
        return new ClientHandleDistributeSingleClientRealCloseVisitorTypeAdvanced();
    }

    @Bean
    public ClientReportHandleChannelTransferTypeAdvancedHandleDistribute handleChannelTransferTypeAdvancedHandleDistribute(NettyServerProperties nettyServerProperties){
        return new ClientReportHandleChannelTransferTypeAdvancedHandleDistribute(nettyServerProperties);
    }
    @Bean
    public HandleDistributeConnectSuccessNotificationTypeAdvancedHandle handleDistributeConnectSuccessNotificationTypeAdvancedHandle(ClientNettyConfigApplication clientNettyConfigApplication){
        return new HandleDistributeConnectSuccessNotificationTypeAdvancedHandle(clientNettyConfigApplication);
    }
    @Bean
    public HandleClientChannelActiveAdvanced handleClientChannelActiveAdvanced(NettyServerProperties nettyServerProperties){
        return new HandleClientChannelActiveAdvanced(nettyServerProperties);
    }
    @Bean
    public HandleDistributeDisconnectTypeAdvancedHandle handleDistributeDisconnectTypeAdvancedHandle(ClientNettyConfigApplication clientNettyConfigApplication){
        return new HandleDistributeDisconnectTypeAdvancedHandle(clientNettyConfigApplication);
    }
    @Bean
    public HandleDistributeStagingClosedTypeAdvanced handleDistributeStagingClosedTypeAdvanced(){
        return new HandleDistributeStagingClosedTypeAdvanced();
    }
    @Bean
    public HandleDistributeStagingOpenedTypeAdvanced handleDistributeStagingOpenedTypeAdvanced(){
        return new HandleDistributeStagingOpenedTypeAdvanced();
    }


    @Bean
    public ClientHandleDistributeSingleClientRealConnectTypeAdvanced clientHandleDistributeSingleClientRealConnectTypeAdvanced(NettyServerProperties nettyServerProperties,
                                                                                                                         List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList){
        return new ClientHandleDistributeSingleClientRealConnectTypeAdvanced(nettyServerProperties, handleChannelTypeAdvancedList);
    }
}
