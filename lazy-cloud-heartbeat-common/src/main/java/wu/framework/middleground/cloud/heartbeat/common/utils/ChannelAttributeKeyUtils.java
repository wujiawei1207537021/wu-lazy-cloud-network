package wu.framework.middleground.cloud.heartbeat.common.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 通道属性绑定工具
 */
public class ChannelAttributeKeyUtils {

    private static final AttributeKey<String> VISITOR_ID = AttributeKey.newInstance("visitorId");
    private static final AttributeKey<String> CLIENT_ID = AttributeKey.newInstance("clientId");


    /**
     * 为通道绑定 访客属性
     *
     * @param channel   通道
     * @param visitorId 访客ID
     */
    public static void buildVisitorId(Channel channel, byte[] visitorId) {
        channel.attr(VISITOR_ID).set(new String(visitorId));
    }

    /**
     * 为通道绑定 访客属性
     *
     * @param channel   通道
     * @param visitorId 访客ID
     */
    public static void buildVisitorId(Channel channel, String visitorId) {
        channel.attr(VISITOR_ID).set(visitorId);
    }

    /**
     * 获取 通道中访客ID
     * @param channel   通道
     */
    public static String getVisitorId(Channel channel) {
        return channel.attr(VISITOR_ID).get();
    }


    /**
     * 为通道绑定 访客属性
     *
     * @param channel   通道
     * @param clientId 客户端ID
     */
    public static void buildClientId(Channel channel, byte[] clientId) {
        channel.attr(CLIENT_ID).set(new String(clientId));
    }
    /**
     * 为通道绑定 访客属性
     *
     * @param channel   通道
     * @param clientId 客户端ID
     */
    public static void buildClientId(Channel channel, String clientId) {
        channel.attr(CLIENT_ID).set(clientId);
    }

    /**
     * 获取 通道中访客ID
     * @param channel   通道
     */
    public static String getClientId(Channel channel) {
        return channel.attr(CLIENT_ID).get();
    }
}
