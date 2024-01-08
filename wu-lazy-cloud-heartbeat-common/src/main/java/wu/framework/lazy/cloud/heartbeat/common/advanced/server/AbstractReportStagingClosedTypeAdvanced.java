package wu.framework.lazy.cloud.heartbeat.common.advanced.server;


import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 客户端暂存通知
 * 客户端离线后陷入暂存服务业务上使用
 * 云端发送的消息，此模式云端后者说服务端不需要处理
 * CLIENT_STAGING
 * 客户端上报暂存
 */

public abstract class AbstractReportStagingClosedTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {

    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.REPORT_CLIENT_STAGING_CLOSED.getTypeByte() == msg.getType();
    }
}
