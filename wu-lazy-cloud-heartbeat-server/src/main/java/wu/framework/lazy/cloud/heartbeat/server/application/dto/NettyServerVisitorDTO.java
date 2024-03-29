package wu.framework.lazy.cloud.heartbeat.server.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 服务端提前开放出来的端口
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDTO
 **/
@Data
@Accessors(chain = true)
@Schema(title = "netty_server_visitor_command_dto", description = "服务端提前开放出来的端口")
public class NettyServerVisitorDTO {


    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime", example = "")
    private LocalDateTime createTime;

    /**
     * 描述
     */
    @Schema(description = "描述", name = "describe", example = "")
    private String describe;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID", name = "id", example = "")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted", example = "")
    private Boolean isDeleted;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime", example = "")
    private LocalDateTime updateTime;

    /**
     * 访客端口
     */
    @Schema(description = "访客端口", name = "visitorPort", example = "")
    private Integer visitorPort;

}