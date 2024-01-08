package wu.framework.middleground.cloud.heartbeat.common.advanced.server;


import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.enums.MessageTypeEnums;


/**
 * 服务端处理客户端绑定真实服务成功
 * REPORT_SINGLE_CLIENT_REAL_CONNECT
 */
public abstract class AbstractReportSingleClientRealConnectTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {


    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.REPORT_SINGLE_CLIENT_REAL_CONNECT.getTypeByte() == msg.getType();
    }
}
