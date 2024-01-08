package wu.framework.middleground.cloud.heartbeat.common.advanced;


import wu.framework.middleground.cloud.heartbeat.common.MessageType;
import wu.framework.middleground.cloud.heartbeat.common.enums.MessageTypeEnums;
import io.netty.channel.Channel;

/**
 * 通道不同数据类型处理器
 *
 * @see MessageType
 * @see MessageTypeEnums
 */
public interface ChannelTypeAdvanced {


    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    void handler(Channel channel, Object msg);

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    boolean support(Object msg);

}
