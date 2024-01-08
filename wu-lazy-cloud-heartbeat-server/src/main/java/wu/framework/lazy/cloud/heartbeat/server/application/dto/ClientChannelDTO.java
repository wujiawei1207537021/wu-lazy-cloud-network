package wu.framework.lazy.cloud.heartbeat.server.application.dto;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import lombok.Data;



/**
 * 客户端通道
 */
@Data
public class ClientChannelDTO {
    /**
     * 连接信息
     */
    private String clientId;
    /**
     * 通道ID
     */
    private ChannelId channelId;
    /**
     * 通道
     */
    private Channel channel;
}
