package wu.framework.lazy.cloud.heartbeat.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 访客端口对应访客上下文
 */
public class NettyClientVisitorContext {

    protected static final ConcurrentHashMap<String/*clientId*/, List<Object>/*NettyVisitorSocket*/> VISITOR_SOCKET = new ConcurrentHashMap<>();


    /**
     * 添加访客
     *
     * @param clientId      客户端ID
     * @param visitorSocket 客户端访客socket
     */
    public static <T> void pushVisitorSocket(String clientId, T visitorSocket) {
        List<Object> visitors = getVisitorSockets(clientId);
        visitors.add(visitorSocket);
        VISITOR_SOCKET.put(clientId, visitors);
    }

    /**
     * 通过客户端ID获取客户端使用的访客socket
     *
     * @param <T>      访客范型
     * @param clientId 客户端ID
     * @return 访客
     */
    public static <T> List<T> getVisitorSockets(String clientId) {
        return (List<T>) VISITOR_SOCKET.getOrDefault(clientId, new ArrayList<>());
    }

    /**
     * 关闭客户端访客socket
     *
     * @param clientId 客户端ID
     */
    public static void close(String clientId) {
//        getVisitorSockets(clientId)
    }

}
