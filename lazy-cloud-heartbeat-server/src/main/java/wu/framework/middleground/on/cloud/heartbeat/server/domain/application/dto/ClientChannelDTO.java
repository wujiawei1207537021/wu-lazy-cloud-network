package wu.framework.middleground.on.cloud.heartbeat.server.domain.application.dto;

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
