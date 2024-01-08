package wu.framework.lazy.cloud.heartbeat.common.advanced.server;


import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.ChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;


/**
 * 服务端处理客户端 关闭一个访客
 * REPORT_SINGLE_CLIENT_CLOSE_VISITOR
 */
public abstract class AbstractReportSingleClientCloseVisitorTypeAdvanced<MSG> extends AbstractChannelTypeAdvanced<NettyProxyMsg> implements ChannelTypeAdvanced {


    /**
     * 是否支持当前类型
     *
     * @param msg 通道数据
     * @return 布尔类型 是、否
     */
    @Override
    public boolean doSupport(NettyProxyMsg msg) {
        return MessageTypeEnums.REPORT_SINGLE_CLIENT_CLOSE_VISITOR.getTypeByte() == msg.getType();
    }
}