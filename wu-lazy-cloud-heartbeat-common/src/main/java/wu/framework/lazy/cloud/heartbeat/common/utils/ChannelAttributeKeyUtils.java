package wu.framework.lazy.cloud.heartbeat.common.utils;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 通道属性绑定工具
 */
public class ChannelAttributeKeyUtils {

    private static final AttributeKey<String> VISITOR_ID = AttributeKey.newInstance("visitorId");
    private static final AttributeKey<Integer> VISITOR_PORT = AttributeKey.newInstance("visitorPort");
    private static final AttributeKey<String> CLIENT_ID = AttributeKey.newInstance("clientId");
    private static final AttributeKey<Integer> OUT_FLOW = AttributeKey.newInstance("outFlow");
    private static final AttributeKey<Integer> IN_FLOW = AttributeKey.newInstance("inFlow");


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
     *
     * @param channel 通道
     */
    public static String getVisitorId(Channel channel) {
        return channel.attr(VISITOR_ID).get();
    }


    /**
     * 为通道绑定 访客属性
     *
     * @param channel  通道
     * @param clientId 客户端ID
     */
    public static void buildClientId(Channel channel, byte[] clientId) {
        channel.attr(CLIENT_ID).set(new String(clientId));
    }

    /**
     * 为通道绑定 访客属性
     *
     * @param channel  通道
     * @param clientId 客户端ID
     */
    public static void buildClientId(Channel channel, String clientId) {
        channel.attr(CLIENT_ID).set(clientId);
    }

    /**
     * 获取 通道中访客ID
     *
     * @param channel 通道
     */
    public static String getClientId(Channel channel) {
        return channel.attr(CLIENT_ID).get();
    }


    /**
     * 为通道绑定 出口流量
     *
     * @param channel 通道
     * @param outFlow 出口流量
     */
    public static void buildOutFlow(Channel channel, Integer outFlow) {
        channel.attr(OUT_FLOW).set(outFlow);
    }

    /**
     * 获取 通道中出口流量
     *
     * @param channel 通道
     */
    public static Integer getOutFlow(Channel channel) {
        return channel.attr(OUT_FLOW).get();
    }

    /**
     * 为通道绑定 进口流量
     *
     * @param channel 通道
     * @param inFlow  进口流量
     */
    public static void buildInFlow(Channel channel, Integer inFlow) {
        channel.attr(IN_FLOW).set(inFlow);
    }

    /**
     * 获取 通道中进口流量
     *
     * @param channel 通道
     */
    public static Integer getInFlow(Channel channel) {
        return channel.attr(IN_FLOW).get();
    }

    /**
     * 为通道绑定 通道中访客端口
     *
     * @param channel     通道
     * @param visitorPort 进口流量
     */
    public static void buildVisitorPort(Channel channel, Integer visitorPort) {
        channel.attr(VISITOR_PORT).set(visitorPort);
    }

    /**
     * 获取 通道中访客端口
     *
     * @param channel 通道
     */
    public static Integer getVisitorPort(Channel channel) {
        return channel.attr(VISITOR_PORT).get();
    }
}
