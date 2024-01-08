package wu.framework.lazy.cloud.heartbeat.common.advanced.client;


import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;


/**
 * 客户端处理服务端下发数据
 * DISTRIBUTE_CLIENT_TRANSFER
 */
public abstract class AbstractDistributeChannelTransferTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {


    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.DISTRIBUTE_CLIENT_TRANSFER.getTypeByte() == msg.getType();
    }
}