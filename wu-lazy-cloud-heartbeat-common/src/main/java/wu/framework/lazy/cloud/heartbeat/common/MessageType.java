package wu.framework.lazy.cloud.heartbeat.common;

import wu.framework.lazy.cloud.heartbeat.common.advanced.AbstractHandleChannelHeartbeatTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.*;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.*;
import wu.framework.lazy.cloud.heartbeat.common.enums.MessageTypeEnums;

/**
 * @see MessageTypeEnums
 * 数据取值范围 -128~ 127
 * 当前约束范围 -100～100
 */
public class MessageType {
    /**
     * 心跳
     *
     * @see MessageTypeEnums#TYPE_HEARTBEAT
     * @see AbstractHandleChannelHeartbeatTypeAdvanced
     */
    public static final byte TYPE_HEARTBEAT = 0X00;

    /**
     * 客户端上报连接成功
     *
     * @see MessageTypeEnums#REPORT_CLIENT_CONNECT_SUCCESS
     * @see AbstractHandleClientConnectSuccessTypeAdvanced
     */
    public static final byte REPORT_CLIENT_CONNECT_SUCCESS = 0X01;
    /**
     * 上报 客户端断开连接
     *
     * @see MessageTypeEnums#REPORT_CLIENT_DISCONNECTION
     * @see AbstractHandleReportDisconnectTypeAdvanced
     */
    public static final byte REPORT_CLIENT_DISCONNECTION = 0X02;
    /**
     * 客户端上报暂存开启
     *
     * @see MessageTypeEnums#REPORT_CLIENT_STAGING_OPENED
     * @see AbstractHandleReportStagingOpenedTypeAdvanced
     */
    public static final byte REPORT_CLIENT_STAGING_OPENED = 0X03;
    /**
     * 客户端上报暂存关闭
     *
     * @see MessageTypeEnums#REPORT_CLIENT_STAGING_CLOSED
     * @see AbstractHandleReportStagingClosedTypeAdvanced
     */
    public static final byte REPORT_CLIENT_STAGING_CLOSED = 0X04;

    /**
     * 上报 客户端数据传输（内网穿透数据回传）
     *
     * @see MessageTypeEnums#REPORT_CLIENT_TRANSFER
     * @see AbstractHandleReportHandleChannelTransferTypeAdvanced
     */
    public static final byte REPORT_CLIENT_TRANSFER = 0X05;


    /**
     * 上报 客户端创建需要代理的真实端口成功
     *
     * @see MessageTypeEnums#REPORT_SINGLE_CLIENT_REAL_CONNECT
     * @see AbstractHandleReportSingleClientRealConnectTypeAdvanced
     */
    public static final byte REPORT_SINGLE_CLIENT_REAL_CONNECT = 0X06;
    /**
     * 上报 客户端关闭一个访客通道
     *
     * @see MessageTypeEnums#REPORT_SINGLE_CLIENT_CLOSE_VISITOR
     * @see AbstractHandleReportSingleClientCloseVisitorTypeAdvanced
     */
    public static final byte REPORT_SINGLE_CLIENT_CLOSE_VISITOR = 0X08;

    /**
     * 上报 客户端消息到另一个客户端
     *
     * @see MessageTypeEnums#REPORT_SINGLE_CLIENT_MESSAGE
     * @see AbstractHandleReportSingleClientMessage2OtherClientTypeAdvanced
     */
    public static final byte REPORT_SINGLE_CLIENT_MESSAGE = 0X09;
    /**
     * 服务端通道 is active
     *
     * @see MessageTypeEnums#SERVER_CHANNEL_ACTIVE
     * @see AbstractHandleServerChannelActiveTypeAdvanced
     */
    public static final byte SERVER_CHANNEL_ACTIVE = 0X10;

    /**
     * 下发 客户端接收连接成功通知
     *
     * @see MessageTypeEnums#DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION
     * @see AbstractHandleDistributeConnectSuccessNotificationTypeAdvancedHandle
     */
    public static final byte DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION = -0X01;
    /**
     * 下发 客户端断开连接通知
     *
     * @see MessageTypeEnums#DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION
     * @see AbstractHandleDistributeDisconnectTypeAdvancedHandle
     */
    public static final byte DISTRIBUTE_CLIENT_DISCONNECTION_NOTIFICATION = -0X02;
    /**
     * 下发 客户端暂存开启通知
     *
     * @see MessageTypeEnums#DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION
     * @see AbstractHandleDistributeStagingOpenedTypeAdvanced
     */
    public static final byte DISTRIBUTE_CLIENT_STAGING_OPENED_NOTIFICATION = -0X03;

    /**
     * 下发 客户端暂存关闭通知
     *
     * @see MessageTypeEnums#DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION
     * @see AbstractHandleDistributeStagingClosedTypeAdvanced
     */
    public static final byte DISTRIBUTE_CLIENT_STAGING_CLOSED_NOTIFICATION = -0X04;
    /**
     * 下发 客户端数据传输(内网穿透数据发送)
     *
     * @see MessageTypeEnums#DISTRIBUTE_CLIENT_TRANSFER
     * @see AbstractHandleDistributeChannelTransferTypeAdvanced
     */
    public static final byte DISTRIBUTE_CLIENT_TRANSFER = -0X05;
    /**
     * 下发 客户端创建需要代理的真实端口
     *
     * @see MessageTypeEnums#DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT
     * @see AbstractHandleDistributeSingleClientRealConnectTypeAdvanced
     */
    public static final byte DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT = -0X06;

    /**
     * 下发 客户端代理的真实端口自动读写
     *
     * @see MessageTypeEnums#DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ
     * @see AbstractHandleDistributeSingleClientRealAutoReadConnectTypeAdvanced
     */
    public static final byte DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT_AUTO_READ = -0X07;

    /**
     * 下发 客户端关闭代理服务通道
     *
     * @see MessageTypeEnums#DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR
     * @see AbstractHandleDistributeSingleClientRealCloseVisitorTypeAdvanced
     */
    public static final byte DISTRIBUTE_SINGLE_CLIENT_REAL_CLOSE_VISITOR = -0X08;

    /**
     * 下发 客户端消息
     *
     * @see MessageTypeEnums#DISTRIBUTE_SINGLE_CLIENT_MESSAGE
     * @see AbstractHandleDistributeSingleClientMessageTypeAdvanced
     */
    public static final byte DISTRIBUTE_SINGLE_CLIENT_MESSAGE = -0X09;


    /**
     * 客户端通道 is active
     *
     * @see MessageTypeEnums#CLIENT_CHANNEL_ACTIVE
     * @see AbstractHandleClientChannelActiveAdvanced
     */
    public static final byte CLIENT_CHANNEL_ACTIVE = -0X10;
}
