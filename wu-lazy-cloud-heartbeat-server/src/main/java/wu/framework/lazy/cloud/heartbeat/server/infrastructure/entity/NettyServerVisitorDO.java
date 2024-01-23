package wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import com.wu.framework.inner.lazy.stereotype.LazyTableIndex;
import com.wu.framework.inner.layer.stereotype.LayerField;
import com.wu.framework.inner.layer.stereotype.LayerField.LayerFieldType;
import com.wu.framework.inner.lazy.stereotype.LazyTable;
import com.wu.framework.inner.lazy.stereotype.LazyTableField;
import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.lang.String;
import com.wu.framework.inner.lazy.stereotype.LazyTableFieldId;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.Integer;
/**
 * describe 服务端提前开放出来的端口 
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "netty_server_visitor",comment = "服务端提前开放出来的端口")
@Schema(title = "netty_server_visitor",description = "服务端提前开放出来的端口")
public class NettyServerVisitorDO {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableField(name="create_time",comment="创建时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    /**
     * 
     * 描述
     */
    @Schema(description ="描述",name ="describe",example = "")
    @LazyTableField(name="describe",comment="描述",columnType="varchar(255)")
    private String describe;

    /**
     * 
     * 用户ID
     */
    @Schema(description ="用户ID",name ="id",example = "")
    @LazyTableFieldId(name = "id", comment = "用户ID")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",columnType="tinyint(1)")
    private Boolean isDeleted;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    @LazyTableField(name="update_time",comment="更新时间",defaultValue="CURRENT_TIMESTAMP",upsertStrategy = LazyFieldStrategy.NEVER,columnType="datetime",extra=" on update CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;

    /**
     * 
     * 访客端口
     */
    @Schema(description ="访客端口",name ="visitorPort",example = "")
    @LazyTableField(name="visitor_port",comment="访客端口",columnType="int")
    private Integer visitorPort;

}