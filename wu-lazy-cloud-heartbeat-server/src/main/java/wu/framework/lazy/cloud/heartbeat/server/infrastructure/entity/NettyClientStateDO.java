package wu.framework.lazy.on.cloud.heartbeat.server.domain.infrastructure.entity;

import wu.framework.lazy.cloud.heartbeat.common.enums.NettyClientStatus;
import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 客户端状态
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "netty_client_state", schema = "middleground_cloud_netty_server", comment = "客户端状态")
@Schema(title = "netty_client_state", description = "客户端状态")
public class NettyClientStateDO {


    /**
     * 客户端ID
     */
    @Schema(description = "客户端ID", name = "clientId", example = "")
    @LazyTableFieldUnique(name = "client_id", comment = "客户端ID", columnType = "varchar(255)")
    private String clientId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", name = "createTime", example = "")
    @LazyTableFieldCreateTime
    private LocalDateTime createTime;

    /**
     * 主键
     */
    @Schema(description = "主键", name = "id", example = "")
    @LazyTableFieldId(name = "id", comment = "主键")
    private Long id;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除", name = "isDeleted", example = "")
    @LazyTableField(name = "is_deleted", comment = "是否删除",defaultValue = "'0'",columnType = "tinyint")
    private Boolean isDeleted;

    /**
     * 在线状态（true在线，false离线）
     */
    @Schema(description = "在线状态（true在线，false离线）", name = "onLineState", example = "")
    @LazyTableField(name = "on_line_state", comment = "在线状态（true在线，false离线）", columnType = "varchar(255)")
    private NettyClientStatus onLineState;

    /**
     * 暂存状态（开启、关闭）
     */
    @Schema(description = "暂存状态（开启、关闭）", name = "staging", example = "")
    @LazyTableField(name = "staging", comment = "暂存状态（开启、关闭）", columnType = "varchar(255)")
    private String stagingState;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间", name = "updateTime", example = "")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;

}