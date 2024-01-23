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
     * @return ServerHandleChannelHeartbeatTypeAdvanced
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleChannelHeartbeatTypeAdvanced serverChannelHeartbeatTypeAdvanced() {
        return new ServerHandleChannelHeartbeatTypeAdvanced();
    }

    /**
     * 处理 服务端处理客户端数据传输
     *
     * @return ServerHandleReportHandleChannelTransferTypeAdvanced
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleReportHandleChannelTransferTypeAdvanced serverReportChannelTransferTypeAdvanced() {
        return new ServerHandleReportHandleChannelTransferTypeAdvanced();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleClientConnectSuccessTypeAdvanced serverReportConnectSuccessTypeAdvanced(
            ServerNettyConfigApplication serverNettyConfigApplication,
            NettyClientBlacklistApplication nettyClientBlacklistApplication,
            InternalNetworkPenetrationMappingApplication internalNetworkPenetrationMappingApplication) {
        return new ServerHandleClientConnectSuccessTypeAdvanced(serverNettyConfigApplication, nettyClientBlacklistApplication, internalNetworkPenetrationMappingApplication);
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleReportDisconnectTypeAdvanced serverReportDisconnectTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        return new ServerHandleReportDisconnectTypeAdvanced(serverNettyConfigApplication);
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleReportSingleClientCloseVisitorTypeAdvanced serverReportSingleClientCloseVisitorTypeAdvanced() {
        return new ServerHandleReportSingleClientCloseVisitorTypeAdvanced();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleReportSingleClientRealConnectTypeAdvanced serverReportSingleClientRealConnectTypeAdvanced() {
        return new ServerHandleReportSingleClientRealConnectTypeAdvanced();
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleReportStagingClosedTypeAdvanced serverReportStagingClosedTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        return new ServerHandleReportStagingClosedTypeAdvanced(serverNettyConfigApplication);
    }

    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandleReportStagingOpenedTypeAdvanced serverReportStagingOpenedTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication) {
        return new ServerHandleReportStagingOpenedTypeAdvanced(serverNettyConfigApplication);
    }


}
