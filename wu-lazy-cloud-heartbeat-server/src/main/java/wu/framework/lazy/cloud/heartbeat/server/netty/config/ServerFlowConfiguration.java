package wu.framework.lazy.cloud.heartbeat.server.netty.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelFlowAdapter;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.HandleChannelFlowAdvanced;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlowRepository;
import wu.framework.lazy.cloud.heartbeat.server.netty.flow.ServerHandlerInFlowHandler;
import wu.framework.lazy.cloud.heartbeat.server.netty.flow.ServerHandlerOutFlowHandler;

import java.util.List;

/**
 * @see ChannelFlowAdapter
 * @see HandleChannelFlowAdvanced
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ServerFlowConfiguration {
    /**
     * 进口数据处理
     *
     * @return ServerHandlerInFlowHandler
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandlerInFlowHandler serverHandlerInFlowHandler(VisitorFlowRepository visitorFlowRepository) {
        return new ServerHandlerInFlowHandler(visitorFlowRepository);
    }

    /**
     * 出口数据处理
     *
     * @return ServerHandlerOutFlowHandler
     */
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    @Bean
    public ServerHandlerOutFlowHandler serverHandlerOutFlowHandler(VisitorFlowRepository visitorFlowRepository) {
        return new ServerHandlerOutFlowHandler(visitorFlowRepository);
    }


    /**
     * 服务端流量适配器
     *
     * @param handleChannelFlowAdvancedList 服务端流量适配者
     * @return 服务端流量适配器
     */
    @ConditionalOnMissingBean(ChannelFlowAdapter.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ChannelFlowAdapter channelFlowAdapter(List<HandleChannelFlowAdvanced> handleChannelFlowAdvancedList) {
        return new ChannelFlowAdapter(handleChannelFlowAdvancedList);
    }

}
