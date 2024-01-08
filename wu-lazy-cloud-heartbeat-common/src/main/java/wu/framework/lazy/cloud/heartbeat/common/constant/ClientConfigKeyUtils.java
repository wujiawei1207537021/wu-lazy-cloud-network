package wu.framework.lazy.cloud.heartbeat.common.constant;

import wu.framework.lazy.cloud.heartbeat.common.enums.NettyClientStatus;

/**
 * 客户端配置 key 常量
 */
public class ClientConfigKeyUtils {

    //
    /**
     * 客户端状态对应的key
     *
     * @see NettyClientStatus#ON_LINE
     * @see NettyClientStatus#OFF_LINE
     */
    public static final String CLIENT_STATUS_KEY = "middleground:cloud:netty:client:status";


    /**
     * 客户端ID存放的key
     */
    public static final String CLIENT_ID_KEY = "middleground:cloud:netty:client:id";


    /**
     * 获取客户端对应的状态key
     *
     * @param clientId 客户端ID
     * @return 客户端对应的状态key
     */
    public static String getClientStatusKey(String clientId) {
        return CLIENT_STATUS_KEY + ":" + clientId;
    }


    /**
     * 获取 客户端ID对应的key
     *
     * @param clientId 客户端ID
     * @return 客户端ID对应的key
     */
    public static String getClientIdKey(String clientId) {
        return CLIENT_ID_KEY + ":" + clientId;
    }
}
