package wu.framework.lazy.cloud.heartbeat.common.advanced.server;

import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractHandleChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * 服务端处理客户端连接成功
 */
public abstract class AbstractHandleClientConnectSuccessTypeAdvanced<MSG> extends AbstractHandleChannelTypeAdvanced<NettyProxyMsg> implements HandleChannelTypeAdvanced {

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
