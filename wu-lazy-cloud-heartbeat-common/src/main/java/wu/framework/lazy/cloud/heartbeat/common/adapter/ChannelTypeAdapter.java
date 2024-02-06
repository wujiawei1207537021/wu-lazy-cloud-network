package wu.framework.lazy.cloud.heartbeat.common.adapter;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;

import java.util.List;

/**
 * 通道类型适配器
 */
@Slf4j
public class ChannelTypeAdapter {
    protected final List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList;

    public ChannelTypeAdapter(List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) {
        this.handleChannelTypeAdvancedList = handleChannelTypeAdvancedList;
    }

    /**
     * 处理当前数据
     *
     * @param msg 通道数据
     */
    public void handler(Channel channel, Object msg) {
        for (HandleChannelTypeAdvanced handleChannelTypeAdvanced : handleChannelTypeAdvancedList) {
            if (handleChannelTypeAdvanced.support(msg)) {
//                log.info("处理器:{},客户端:{}, 处理类型:{}",handleChannelTypeAdvanced.getClass(),new String(msg.getClientId()),msg.getType());
                handleChannelTypeAdvanced.handler(channel, msg);
                return;
            }
        }
    }


}
