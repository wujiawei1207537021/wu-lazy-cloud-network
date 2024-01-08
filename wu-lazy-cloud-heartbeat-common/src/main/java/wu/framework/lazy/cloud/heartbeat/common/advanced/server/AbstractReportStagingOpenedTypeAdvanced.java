package wu.framework.lazy.cloud.heartbeat.common.advanced.server;


import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 上报客户端暂存开启
 */

public abstract class AbstractReportStagingOpenedTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.REPORT_CLIENT_STAGING_OPENED.getTypeByte() == msg.getType();
    }
}
