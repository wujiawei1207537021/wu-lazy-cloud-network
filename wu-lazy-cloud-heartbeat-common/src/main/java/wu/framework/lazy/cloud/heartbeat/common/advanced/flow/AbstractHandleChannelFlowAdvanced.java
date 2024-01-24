package wu.framework.lazy.cloud.heartbeat.common.advanced.flow;

import io.netty.channel.Channel;

/**
 * 处理通道流量适配者 抽象类
 *
 * @see HandleChannelFlowAdvanced
 */
public abstract class AbstractHandleChannelFlowAdvanced implements HandleChannelFlowAdvanced {

    /**
     * 是否支持当前这种类型
     *
     * @param channelFlow 数据
     * @return boolean
     */
    @Override
    public boolean support(ChannelFlow channelFlow) {
        return doSupport(channelFlow);
    }

    /**
     * 处理是否支持这种类型
     * @param channelFlow 数据
     * @return boolean
     */
    protected abstract boolean doSupport(ChannelFlow channelFlow);

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param channelFlow     通道数据
     */
    @Override
    public void handler(Channel channel, ChannelFlow channelFlow) {
        doHandler(channel, channelFlow);
    }
    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param channelFlow     通道数据
     */
    protected  abstract void doHandler(Channel channel, ChannelFlow channelFlow);
}
