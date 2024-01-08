package wu.framework.middleground.cloud.heartbeat.common.advanced;


import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.enums.MessageTypeEnums;


/**
 * 服务端 处理客户端心跳
 * TYPE_HEARTBEAT
 */

public abstract class AbstractChannelHeartbeatTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.TYPE_HEARTBEAT.getTypeByte() == msg.getType();
    }
}
