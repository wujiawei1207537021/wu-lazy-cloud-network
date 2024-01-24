package wu.framework.lazy.cloud.heartbeat.server.netty.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelFlowAdapter;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.HandleChannelFlowAdvanced;

import java.util.List;

/**
 * @see ChannelFlowAdapter
 * @see HandleChannelFlowAdvanced
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
public class ServerFlowConfiguration {


    /**
     * 服务端流量适配器
     * @param handleChannelFlowAdvancedList 服务端流量适配者
     * @return 服务端流量适配器
     */
    @ConditionalOnMissingBean(ChannelFlowAdapter.class)
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ChannelFlowAdapter channelFlowAdapter(List<HandleChannelFlowAdvanced> handleChannelFlowAdvancedList){
        return new ChannelFlowAdapter(handleChannelFlowAdvancedList);
    }

}
