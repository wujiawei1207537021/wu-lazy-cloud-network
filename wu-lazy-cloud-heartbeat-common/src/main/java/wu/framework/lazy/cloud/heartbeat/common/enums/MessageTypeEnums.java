package wu.framework.lazy.cloud.heartbeat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractChannelHeartbeatTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.*;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.*;
import wu.framework.middleground.cloud.heartbeat.common.advanced.client.*;
import wu.framework.middleground.cloud.heartbeat.common.advanced.server.*;

/**
 * @see MessageType
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnums {
    /**
     * @see AbstractChannelHeartbeatTypeAdvanced
     */
    TYPE_HEARTBEAT(MessageType.TYPE_HEARTBEAT, "心跳"),
    /**
     * @see AbstractReportConnectSuccessTypeAdvanced
     */
    REPORT_CLIENT_CONNECT_SUCCESS(MessageType.REPORT_CLIENT_CONNECT_SUCCESS, "上报 客户端连接成功"),
    /**
     * @see AbstractReportDisconnectTypeAdvanced
     */
    REPORT_CLIENT_DISCONNECTION(MessageType.REPORT_CLIENT_DISCONNECTION, "上报 客户端断开连接"),
    /**
     * @see AbstractReportStagingOpenedTypeAdvanced
     */
    REPORT_CLIENT_STAGING_OPENED(MessageType.REPORT_CLIENT_STAGING_OPENED, "上报 客户端暂存开启"),
    /**
     * @see AbstractReportStagingClosedTypeAdvanced
     */
    REPORT_CLIENT_STAGING_CLOSED(MessageType.REPORT_CLIENT_STAGING_CLOSED, "上报 客户端暂存关闭"),
    /**
     * @see AbstractReportChannelTransferTypeAdvanced
     */

    REPORT_CLIENT_TRANSFER(MessageType.REPORT_CLIENT_TRANSFER, "上报 客户端数据传输（内网穿透数据回传）"),
    /**
     * @see AbstractReportSingleClientRealConnectTypeAdvanced
     */
    REPORT_SINGLE_CLIENT_REAL_CONNECT(MessageType.REPORT_SINGLE_CLIENT_REAL_CONNECT, "上报 客户端创建需要代理的真实端口成功"),
    /**
     * @see AbstractReportSingleClientCloseVisitorTypeAdvanced
     */
    REPORT_SINGLE_CLIENT_CLOSE_VISITOR(MessageType.REPORT_SINGLE_CLIENT_CLOSE_VISITOR, "上报 客户端关闭一个访客通道"),
    /**
     * @see AbstractDistributeConnectSuccessNotificationTypeAdvanced
     */

    DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION, "下发 客户端接收连接成功通知"),




    /**
     * @see AbstractDistributeDisconnectTypeAdvanced
     */
    DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION, "下发 客户端断开连接通知"),
    /**
     * @see AbstractDistributeStagingOpenedTypeAdvanced
     */
    DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION, "下发 客户端暂存开启通知"),
    /**
     * @see AbstractDistributeStagingClosedTypeAdvanced
     */
    DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION, "下发 客户端暂存关闭通知"),
    /**
     * @see AbstractDistributeChannelTransferTypeAdvanced
     */
    DISTRIBUTE_CLIENT_TRANSFER(MessageType.DISTRIBUTE_CLIENT_TRANSFER, "下发 客户端数据传输(内网穿透数据发送)"),
    /**
     * @see AbstractDistributeSingleClientRealConnectTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT, "下发 客户端创建需要代理的真实端口"),
    /**
     * @see AbstractDistributeSingleClientRealAutoReadConnectTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ, "下发 客户端代理的真实端口自动读写"),
    /**
     * @see AbstractDistributeSingleClientRealCloseVisitorTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR, "下发 客户端关闭代理服务通道"),

    ;

    private final byte typeByte;
    private final String desc;
}
