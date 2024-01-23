package wu.framework.lazy.cloud.heartbeat.common;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通道上下文
 */
@Slf4j
public class ChannelContext {

    private final static ConcurrentHashMap<String/*clientId*/, ClientChannelImpl/*通道*/>
            channelIdClientChannelDTOConcurrentHashMap = new ConcurrentHashMap<>();

    /**
     * 新增通道
     *
     * @param channel  通道
     * @param clientId 客户端ID
     */
    public static void push(Channel channel, String clientId) {

        ChannelId channelId = channel.id();
        ClientChannelImpl clientChannelImpl = new ClientChannelImpl();
        clientChannelImpl.setChannelId(channelId);
        clientChannelImpl.setChannel(channel);
        clientChannelImpl.setClientId(clientId.getBytes(StandardCharsets.UTF_8));
        // 如果客户端已经存在 移除
        if(channelIdClientChannelDTOConcurrentHashMap.containsKey(clientId)){
//            clear(clientId);
        }
        channelIdClientChannelDTOConcurrentHashMap.put(clientId, clientChannelImpl);

    }
    /**
     * 新增通道
     *
     * @param channel  通道
     * @param clientId 客户端ID
     */
    public static void push(Channel channel, byte[] clientId) {

        ChannelId channelId = channel.id();
        ClientChannelImpl clientChannelImpl = new ClientChannelImpl();
        clientChannelImpl.setChannelId(channelId);
        clientChannelImpl.setChannel(channel);
        clientChannelImpl.setClientId(clientId);
        channelIdClientChannelDTOConcurrentHashMap.put(new String(clientId), clientChannelImpl);

    }

    /**
     * 获取所有通道
     *
     * @return 返回所有通道信息
     */
    public static List<ClientChannel> get() {
        return new ArrayList<>(channelIdClientChannelDTOConcurrentHashMap.values());
    }


    /**
     * 根据通道ID获取通道信息
     *
     * @param clientId 客户端ID
     * @return 通道信息
     */
    public static ClientChannel get(byte[] clientId) {
        if (channelIdClientChannelDTOConcurrentHashMap
                .containsKey(new String(clientId))) {
            return channelIdClientChannelDTOConcurrentHashMap
                    .get(new String(clientId));
        } else {
            log.error("无法通过客户端ID[" + new String(clientId) + "]获取通道信息");
            return null;
        }
    }

    /**
     * 根据通道ID获取通道信息
     *
     * @param clientId 客户端ID
     * @return 通道信息
     */
    public static ChannelContext.ClientChannel get(String clientId) {
        return get(clientId.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 关闭通道
     *
     * @param clientId 客户端ID
     */
    public static void clear(String clientId) {
        ClientChannel clientChannel = get(clientId);
        if (clientChannel != null) {
            remove(clientId);
            Channel channel = clientChannel.getChannel();
            if (channel != null && channel.isActive()) {
                channel.close();
            }
        } else {
            // log warm
            log.warn("无法通过客户ID:[{}]移除客户端", clientId);
        }
    }

    /**
     * 通过客户端ID移除客户端通道
     *
     * @param clientId 客户端ID
     */
    public static void remove(byte[] clientId) {
        ClientChannel clientChannel = get(clientId);
        if (clientChannel != null) {
            channelIdClientChannelDTOConcurrentHashMap.remove(new String(clientId));
        } else {
            // log warm
            log.warn("无法通过客户ID:[{}]移除客户端", new String(clientId));
        }
    }

    /**
     * 通过客户端ID移除客户端通道
     *
     * @param clientId 客户端ID
     */
    public static void remove(String clientId) {
        ClientChannel clientChannel = get(clientId);
        if (clientChannel != null) {
            channelIdClientChannelDTOConcurrentHashMap.remove(clientId);
        } else {
            // log warm
            log.warn("无法通过客户ID:[{}]移除客户端", clientId);
        }
    }


    /**
     * 客户端通道信息
     */
    public interface ClientChannel {

        /**
         * 客户端ID
         */
        byte[] getClientId();

        /**
         * 通道ID
         */
        ChannelId getChannelId();

        /**
         * 通道
         */
        Channel getChannel();

    }

}

/**
 * 客户端通道信息
 */
@Data
class ClientChannelImpl implements ChannelContext.ClientChannel {
    /**
     * 客户端ID
     */
    private byte[] clientId;
    /**
     * 通道ID
     */
    private ChannelId channelId;
    /**
     * 通道
     */
    private Channel channel;

    @Override
    public String toString() {
        return "ClientChannelImpl{" +
                "clientId=" + new String(clientId) +
                ", channelId=" + channelId.asLongText() +
                '}';
    }
}
