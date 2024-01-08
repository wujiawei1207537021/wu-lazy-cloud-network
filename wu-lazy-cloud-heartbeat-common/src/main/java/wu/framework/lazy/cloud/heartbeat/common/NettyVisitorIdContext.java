package wu.framework.lazy.cloud.heartbeat.common;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 访客ID对应上下文
 */
@Deprecated
public class NettyVisitorIdContext {

    protected static final ConcurrentHashMap<String, Object> VISITOR_ID = new ConcurrentHashMap<>();


    /**
     * 添加访客
     *
     * @param visitorId 访客id
     * @param visitor   访客
     */
    public static <T> void pushVisitor(T visitor, String visitorId) {
        VISITOR_ID.put(visitorId, visitor);

    }

    /**
     * 通过访客端口获取访客
     *
     * @param visitorId 访客id
     * @param <T>       访客范型
     * @return 访客
     */
    public static <T> T getVisitor(String visitorId) {
        return (T) VISITOR_ID.get(visitorId);
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
            VISITOR_ID.remove(visitorId);
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
