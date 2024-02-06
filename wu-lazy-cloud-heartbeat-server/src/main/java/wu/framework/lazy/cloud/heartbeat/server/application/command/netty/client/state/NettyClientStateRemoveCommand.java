package wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import wu.framework.lazy.cloud.heartbeat.common.enums.NettyClientStatus;

import java.time.LocalDateTime;

/**
 * describe 客户端状态
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand
 **/
@Data
@Accessors(chain = true)
@Schema(title = "netty_client_state_remove_command", description = "客户端状态")
public class NettyClientStateRemoveCommand {


    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID", name = "clientId", example = "")
    private String clientId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime", example = "")
    private LocalDateTime createTime;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id", example = "")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted", example = "")
    private Boolean isDeleted;

    /**
     * 在线状态（true在线，false离线）
     */
    @Schema(description = "在线状态（true在线，false离线）", name = "onLineState", example = "")
    private NettyClientStatus onLineState;

    /**
     * 暂存状态（开启、关闭）
     */
    @Schema(description = "暂存状态（开启、关闭）", name = "staging", example = "")
    private String stagingState;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间", name = "updateTime", example = "")
    private LocalDateTime updateTime;

}