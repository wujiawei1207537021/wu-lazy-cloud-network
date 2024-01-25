package wu.framework.lazy.cloud.heartbeat.common.advanced;


import io.netty.channel.Channel;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 通道不同数据类型处理器
 *
 * @see MessageType
 * @see MessageTypeEnums
 */
public interface HandleChannelTypeAdvanced {


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
