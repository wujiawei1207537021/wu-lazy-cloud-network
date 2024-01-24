package wu.framework.lazy.cloud.heartbeat.common.advanced.flow;

import io.netty.channel.Channel;

/**
 * 处理通道流量适配者
 */
public interface HandleChannelFlowAdvanced {

    /**
     * 是否支持当前这种类型
     *
     * @param channelFlow 数据
     * @return boolean
     */
    boolean support(ChannelFlow channelFlow);

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param channelFlow     通道数据
     */
    void handler(Channel channel, ChannelFlow channelFlow);
}
