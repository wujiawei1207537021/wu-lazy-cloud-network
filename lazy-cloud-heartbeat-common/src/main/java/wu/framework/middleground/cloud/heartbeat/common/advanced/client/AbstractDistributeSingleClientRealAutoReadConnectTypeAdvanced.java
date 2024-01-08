package wu.framework.middleground.cloud.heartbeat.common.advanced.client;


import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 下发 客户端代理的真实端口自动读写
 *
 * @see MessageTypeEnums#DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ
 */

public abstract class AbstractDistributeSingleClientRealAutoReadConnectTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ.getTypeByte() == msg.getType();
    }
}
