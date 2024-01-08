package wu.framework.lazy.cloud.heartbeat.common.constant;

/**
 * redis 通道
 */
public class RedisChannelConstant {
    // 客户端离线或者在线通知通道
    public static final String REDIS_CLIENT_ONLINE_OR_OFFLINE_CHANNEL = "REDIS_CLIENT_ONLINE_OR_OFFLINE_CHANNEL";

    /**
     * 客户端监听 客户端暂存开启、关闭通道
     */
    public static final String REDIS_CLIENT_STAGING_OPENED_OR_CLOSED_CHANNEL = "REDIS_CLIENT_STAGING_OPENED_OR_CLOSED_CHANNEL";
}
