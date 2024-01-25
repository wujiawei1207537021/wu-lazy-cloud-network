package wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe 发送请求到客户端
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "netty_client_message_command", description = "客户端状态")
public class NettyClientMessageCommand {


    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID", name = "clientId", example = "")
    private String clientId;


    /**
     * 发送的消息
     */
    @Schema(description = "发送的消息", name = "message", example = "")
    private String message;

}