package wu.framework.lazy.cloud.heartbeat.common.advanced.client;


import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 下发客户端断开连接通知
 * DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION
 */
public abstract class AbstractDistributeDisconnectTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        // 下发 客户端断开连接通知
        return MessageTypeEnums.DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION.getTypeByte() == msg.getType();
    }
}
