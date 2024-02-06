package wu.framework.lazy.cloud.heartbeat.common.adapter;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.ChannelFlow;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.HandleChannelFlowAdvanced;

import java.util.List;

/**
 * 通道流量适配器
 *
 * @see HandleChannelFlowAdvanced
 */
@Slf4j
public class ChannelFlowAdapter {

    protected final List<HandleChannelFlowAdvanced> handleChannelFlowAdvancedList;

    public ChannelFlowAdapter(List<HandleChannelFlowAdvanced> handleChannelFlowAdvancedList) {
        this.handleChannelFlowAdvancedList = handleChannelFlowAdvancedList;
    }

    /**
     * 处理当前数据
     *
     * @param channelFlow 通道数据
     */
    public void handler(Channel channel, ChannelFlow channelFlow) {
        for (HandleChannelFlowAdvanced handleChannelTypeAdvanced : handleChannelFlowAdvancedList) {
            if (handleChannelTypeAdvanced.support(channelFlow)) {
                try {
                    handleChannelTypeAdvanced.handler(channel, channelFlow);
                } catch (Exception e) {
                    log.error("流量统计失败:{}", e.getMessage());
                }
                return;
            }
        }
    }
}
