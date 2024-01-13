package wu.framework.lazy.cloud.heartbeat.server.netty.config;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import wu.framework.lazy.cloud.heartbeat.server.application.InternalNetworkPenetrationMappingApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.NettyClientBlacklistApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.ServerNettyConfigApplication;
import wu.framework.lazy.cloud.heartbeat.server.netty.advanced.*;


@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class HeartbeatServerConfiguration {

    /**
     * 服务端 处理客户端心跳
     *
     * @return ServerChannelHeartbeatTypeAdvanced
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerChannelHeartbeatTypeAdvanced serverChannelHeartbeatTypeAdvanced() {
        return new ServerChannelHeartbeatTypeAdvanced();
    }

    /**
     * 处理 服务端处理客户端数据传输
     *
     * @return ServerReportChannelTransferTypeAdvanced
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportChannelTransferTypeAdvanced serverReportChannelTransferTypeAdvanced() {
        return new ServerReportChannelTransferTypeAdvanced();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportConnectSuccessTypeAdvanced serverReportConnectSuccessTypeAdvanced(
            ServerNettyConfigApplication serverNettyConfigApplication,
            NettyClientBlacklistApplication nettyClientBlacklistApplication,
            InternalNetworkPenetrationMappingApplication internalNetworkPenetrationMappingApplication) {
        return new ServerReportConnectSuccessTypeAdvanced(serverNettyConfigApplication, nettyClientBlacklistApplication, internalNetworkPenetrationMappingApplication);
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportDisconnectTypeAdvanced serverReportDisconnectTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        return new ServerReportDisconnectTypeAdvanced(serverNettyConfigApplication);
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportSingleClientCloseVisitorTypeAdvanced serverReportSingleClientCloseVisitorTypeAdvanced() {
        return new ServerReportSingleClientCloseVisitorTypeAdvanced();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportSingleClientRealConnectTypeAdvanced serverReportSingleClientRealConnectTypeAdvanced() {
        return new ServerReportSingleClientRealConnectTypeAdvanced();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportStagingClosedTypeAdvanced serverReportStagingClosedTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        return new ServerReportStagingClosedTypeAdvanced(serverNettyConfigApplication);
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerReportStagingOpenedTypeAdvanced serverReportStagingOpenedTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        return new ServerReportStagingOpenedTypeAdvanced(serverNettyConfigApplication);
    }


}
