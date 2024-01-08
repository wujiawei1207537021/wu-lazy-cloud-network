package wu.framework.lazy.cloud.heartbeat.common.adapter;

import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 通道类型适配器
 */
@Slf4j
public class ChannelTypeAdapter {
    protected final List<ChannelTypeAdvanced> channelTypeAdvancedList;

    public ChannelTypeAdapter(List<ChannelTypeAdvanced> channelTypeAdvancedList) {
        this.channelTypeAdvancedList = channelTypeAdvancedList;
    }

    /**
     * 处理当前数据
     *
     * @param msg 通道数据
     */
    public void handler(Channel channel, Object msg) {
        for (ChannelTypeAdvanced channelTypeAdvanced : channelTypeAdvancedList) {
            if (channelTypeAdvanced.support(msg)) {
//                log.info("处理器:{},客户端:{}, 处理类型:{}",channelTypeAdvanced.getClass(),new String(msg.getClientId()),msg.getType());
                channelTypeAdvanced.handler(channel, msg);
                return;
            }
        }
    }


}
