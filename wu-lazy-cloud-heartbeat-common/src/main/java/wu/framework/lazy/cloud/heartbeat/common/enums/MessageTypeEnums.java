package wu.framework.lazy.cloud.heartbeat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractHandleChannelHeartbeatTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.*;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.*;

/**
 * @see MessageType
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnums {
    /**
     * @see AbstractHandleChannelHeartbeatTypeAdvanced
     */
    TYPE_HEARTBEAT(MessageType.TYPE_HEARTBEAT, "心跳"),
    /**
     * @see AbstractHandleReportConnectSuccessTypeAdvanced
     */
    REPORT_CLIENT_CONNECT_SUCCESS(MessageType.REPORT_CLIENT_CONNECT_SUCCESS, "上报 客户端连接成功"),
    /**
     * @see AbstractHandleReportDisconnectTypeAdvanced
     */
    REPORT_CLIENT_DISCONNECTION(MessageType.REPORT_CLIENT_DISCONNECTION, "上报 客户端断开连接"),
    /**
     * @see AbstractHandleReportStagingOpenedTypeAdvanced
     */
    REPORT_CLIENT_STAGING_OPENED(MessageType.REPORT_CLIENT_STAGING_OPENED, "上报 客户端暂存开启"),
    /**
     * @see AbstractHandleReportStagingClosedTypeAdvanced
     */
    REPORT_CLIENT_STAGING_CLOSED(MessageType.REPORT_CLIENT_STAGING_CLOSED, "上报 客户端暂存关闭"),
    /**
     * @see AbstractHandleReportHandleChannelTransferTypeAdvanced
     */

    REPORT_CLIENT_TRANSFER(MessageType.REPORT_CLIENT_TRANSFER, "上报 客户端数据传输（内网穿透数据回传）"),
    /**
     * @see AbstractHandleReportSingleClientRealConnectTypeAdvanced
     */
    REPORT_SINGLE_CLIENT_REAL_CONNECT(MessageType.REPORT_SINGLE_CLIENT_REAL_CONNECT, "上报 客户端创建需要代理的真实端口成功"),
    /**
     * @see AbstractHandleReportSingleClientCloseVisitorTypeAdvanced
     */
    REPORT_SINGLE_CLIENT_CLOSE_VISITOR(MessageType.REPORT_SINGLE_CLIENT_CLOSE_VISITOR, "上报 客户端关闭一个访客通道"),
    /**
     * @see AbstractHandleReportSingleClientMessage2OtherClientTypeAdvanced
     */
    REPORT_SINGLE_CLIENT_MESSAGE(MessageType.REPORT_SINGLE_CLIENT_MESSAGE, "上报 客户端消息到另一个客户端"),
    /**
     * @see AbstractHandleDistributeConnectSuccessNotificationTypeAdvancedHandle
     */

    DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION, "下发 客户端接收连接成功通知"),




    /**
     * @see AbstractHandleDistributeDisconnectTypeAdvancedHandle
     */
    DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION, "下发 客户端断开连接通知"),
    /**
     * @see AbstractHandleDistributeStagingOpenedTypeAdvanced
     */
    DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION, "下发 客户端暂存开启通知"),
    /**
     * @see AbstractHandleDistributeStagingClosedTypeAdvanced
     */
    DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION(MessageType.DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION, "下发 客户端暂存关闭通知"),
    /**
     * @see AbstractHandleDistributeChannelTransferTypeAdvanced
     */
    DISTRIBUTE_CLIENT_TRANSFER(MessageType.DISTRIBUTE_CLIENT_TRANSFER, "下发 客户端数据传输(内网穿透数据发送)"),
    /**
     * @see AbstractHandleDistributeSingleClientRealConnectTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT, "下发 客户端创建需要代理的真实端口"),
    /**
     * @see AbstractHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ, "下发 客户端代理的真实端口自动读写"),
    /**
     * @see AbstractHandleDistributeSingleClientRealCloseVisitorTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR, "下发 客户端关闭代理服务通道"),
    /**
     * @see AbstractHandleDistributeSingleClientMessageTypeAdvanced
     */
    DISTRIBUTE_SINGLE_CLIENT_MESSAGE(MessageType.DISTRIBUTE_SINGLE_CLIENT_MESSAGE, "下发 客户端消息"),

    ;

    private final byte typeByte;
    private final String desc;
}
