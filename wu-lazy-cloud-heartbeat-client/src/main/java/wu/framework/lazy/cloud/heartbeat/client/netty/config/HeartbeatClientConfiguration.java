package wu.framework.lazy.cloud.heartbeat.client.netty.config;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import wu.framework.lazy.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import wu.framework.lazy.cloud.heartbeat.client.netty.advanced.*;
import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;

import java.util.List;
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class HeartbeatClientConfiguration {

    /**
     * 服务端 处理客户端心跳
     *
     * @return ClientChannelHeartbeatTypeAdvanced
     */
    @Bean
    public ClientChannelHeartbeatTypeAdvanced clientChannelHeartbeatTypeAdvanced() {
        return new ClientChannelHeartbeatTypeAdvanced();
    }

    /**
     * 处理 客户端代理的真实端口自动读写
     * @return ClientDistributeSingleClientRealAutoReadConnectTypeAdvanced
     */
    @Bean
    public ClientDistributeSingleClientRealAutoReadConnectTypeAdvanced clientDistributeSingleClientRealAutoReadConnectTypeAdvanced(){
        return new ClientDistributeSingleClientRealAutoReadConnectTypeAdvanced();
    }
    @Bean
    public ClientDistributeSingleClientRealCloseVisitorTypeAdvanced clientDistributeSingleClientRealCloseVisitorTypeAdvanced(){
        return new ClientDistributeSingleClientRealCloseVisitorTypeAdvanced();
    }

    @Bean
    public ClientReportChannelTransferTypeAdvanced clientReportChannelTransferTypeAdvanced(NettyServerProperties nettyServerProperties){
        return new ClientReportChannelTransferTypeAdvanced(nettyServerProperties);
    }
    @Bean
    public DistributeConnectSuccessNotificationTypeAdvanced distributeConnectSuccessNotificationTypeAdvanced(ClientNettyConfigApplication clientNettyConfigApplication, NettyServerProperties nettyServerProperties){
        return new DistributeConnectSuccessNotificationTypeAdvanced(clientNettyConfigApplication, nettyServerProperties);
    }
    @Bean
    public DistributeDisconnectTypeAdvanced distributeDisconnectTypeAdvanced(ClientNettyConfigApplication clientNettyConfigApplication){
        return new DistributeDisconnectTypeAdvanced(clientNettyConfigApplication);
    }
    @Bean
    public DistributeStagingClosedTypeAdvanced distributeStagingClosedTypeAdvanced(){
        return new DistributeStagingClosedTypeAdvanced();
    }
    @Bean
    public DistributeStagingOpenedTypeAdvanced distributeStagingOpenedTypeAdvanced(){
        return new DistributeStagingOpenedTypeAdvanced();
    }


    @Bean
    public ClientDistributeSingleClientRealConnectTypeAdvanced clientDistributeSingleClientRealConnectTypeAdvanced(NettyServerProperties nettyServerProperties,
                                                                                                                   List<ChannelTypeAdvanced> channelTypeAdvancedList  ){
        return new ClientDistributeSingleClientRealConnectTypeAdvanced(nettyServerProperties,channelTypeAdvancedList);
    }
}
