package wu.framework.middleground.cloud.heartbeat.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 访客端口对应上下文
 */
public class NettyVisitorContext {

    protected static final ConcurrentHashMap<Integer, Object> VISITOR_PORT = new ConcurrentHashMap<>();


    /**
     * 添加访客
     *
     * @param visitorPort 访客端口
     * @param visitor     访客
     */
    public static <T> void pushVisitor(Integer visitorPort, T visitor) {
        VISITOR_PORT.put(visitorPort, visitor);

    }

    /**
     * 通过访客端口获取访客
     *
     * @param visitorPort 访客端口
     * @param <T>         访客范型
     * @return 访客
     */
    public static <T> T getVisitor(Integer visitorPort) {
        return (T) VISITOR_PORT.get(visitorPort);
    }

}
