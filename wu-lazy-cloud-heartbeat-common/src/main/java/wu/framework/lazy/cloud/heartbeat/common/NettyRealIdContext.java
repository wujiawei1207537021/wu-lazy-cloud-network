package wu.framework.lazy.cloud.heartbeat.common;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 真实通道对应上下文
 */
public class NettyRealIdContext {

    protected static final ConcurrentHashMap<String, Object> REAL = new ConcurrentHashMap<>();


    /**
     * 添加访客
     *
     * @param visitorId 访客id
     * @param visitor   访客
     */
    public static <T> void pushVisitor(T visitor, String visitorId) {
        REAL.put(visitorId, visitor);

    }

    /**
     * 通过访客端口获取访客
     *
     * @param visitorId 访客id
     * @param <T>       访客范型
     * @return 访客
     */
    public static <T> T getVisitor(String visitorId) {
        return (T) REAL.get(visitorId);
    }

    /**
     * 通过访客端口获取访客
     *
     * @param visitorId 访客id
     * @param <T>       访客范型
     * @return 访客
     */
    public static <T> T getVisitor(byte[] visitorId) {
        return getVisitor(new String(visitorId));

    }

    /**
     * 移除访客
     *
     * @param visitorId 访客ID
     */
    public static void clear(String visitorId) {
        Channel visitor = getVisitor(visitorId);
        if (visitor != null) {
            REAL.remove(visitorId);
            visitor.close();
        }

    }

    /**
     * 移除访客
     *
     * @param visitorId 访客ID
     */
    public static void clear(byte[] visitorId) {
        clear(new String(visitorId));
    }
}
