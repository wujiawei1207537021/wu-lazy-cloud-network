package wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * describe 客户端黑名单
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "netty_client_blacklist",  comment = "客户端黑名单")
@Schema(title = "netty_client_blacklist", description = "客户端黑名单")
public class NettyClientBlacklistDO {


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
    @LazyTableField(name = "is_deleted", comment = "是否删除", columnType = "tinyint")
    private Boolean isDeleted;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间", name = "updateTime", example = "")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;

}