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
    public ClientHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced clientDistributeSingleClientRealAutoReadConnectTypeAdvanced(){
        return new ClientHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced();
    }
    /**
     * 处理 接收服务端发送过来的聊天信息
     * @return ClientHandleDistributeSingleClientMessageTypeAdvanced
     */
    @Bean
    public ClientHandleDistributeSingleClientMessageTypeAdvanced clientDistributeSingleClientMessageTypeAdvanced(){
        return new ClientHandleDistributeSingleClientMessageTypeAdvanced();
    }
    @Bean
    public ClientHandleDistributeSingleClientRealCloseVisitorTypeAdvanced clientDistributeSingleClientRealCloseVisitorTypeAdvanced(){
        return new ClientHandleDistributeSingleClientRealCloseVisitorTypeAdvanced();
    }

    @Bean
    public ClientReportHandleChannelTransferTypeAdvancedHandleDistribute clientReportChannelTransferTypeAdvanced(NettyServerProperties nettyServerProperties){
        return new ClientReportHandleChannelTransferTypeAdvancedHandleDistribute(nettyServerProperties);
    }
    @Bean
    public HandleDistributeConnectSuccessNotificationTypeAdvancedHandle distributeConnectSuccessNotificationTypeAdvanced(ClientNettyConfigApplication clientNettyConfigApplication, NettyServerProperties nettyServerProperties){
        return new HandleDistributeConnectSuccessNotificationTypeAdvancedHandle(clientNettyConfigApplication, nettyServerProperties);
    }
    @Bean
    public HandleDistributeDisconnectTypeAdvancedHandle distributeDisconnectTypeAdvanced(ClientNettyConfigApplication clientNettyConfigApplication){
        return new HandleDistributeDisconnectTypeAdvancedHandle(clientNettyConfigApplication);
    }
    @Bean
    public HandleDistributeStagingClosedTypeAdvanced distributeStagingClosedTypeAdvanced(){
        return new HandleDistributeStagingClosedTypeAdvanced();
    }
    @Bean
    public HandleDistributeStagingOpenedTypeAdvanced distributeStagingOpenedTypeAdvanced(){
        return new HandleDistributeStagingOpenedTypeAdvanced();
    }


    @Bean
    public ClientHandleDistributeSingleClientRealConnectTypeAdvanced clientDistributeSingleClientRealConnectTypeAdvanced(NettyServerProperties nettyServerProperties,
                                                                                                                         List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList){
        return new ClientHandleDistributeSingleClientRealConnectTypeAdvanced(nettyServerProperties, handleChannelTypeAdvancedList);
    }
}
