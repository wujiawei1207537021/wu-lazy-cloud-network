package wu.framework.middleground.cloud.heartbeat.common.advanced.server;

import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 客户端连接成功上报处理器
 */
public abstract class AbstractReportConnectSuccessTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.REPORT_CLIENT_CONNECT_SUCCESS.getTypeByte() == msg.getType();
    }
}