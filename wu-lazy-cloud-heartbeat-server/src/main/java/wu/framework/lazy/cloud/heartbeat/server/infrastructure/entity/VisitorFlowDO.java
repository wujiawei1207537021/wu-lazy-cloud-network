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
import java.lang.String;
import java.time.LocalDateTime;
import java.lang.Long;
import java.lang.Integer;
import java.lang.Boolean;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "visitor_flow",schema = "wu_lazy_cloud_netty_server",comment = "访客端流量")
@Schema(title = "visitor_flow",description = "访客端流量")
public class VisitorFlowDO {


    /**
     * 
     * 客户端ID
     */
    @Schema(description ="客户端ID",name ="clientId",example = "")
    @LazyTableFieldUnique(name="client_id",comment="客户端ID",columnType="varchar(50)")
    private String clientId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableFieldCreateTime
    private LocalDateTime createTime;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    @LazyTableFieldId(name="id",comment="主键")
    private Long id;

    /**
     * 
     * 当前访客当前进口流量
     */
    @Schema(description ="当前访客当前进口流量",name ="inFlow",example = "")
    @LazyTableField(name="in_flow",comment="当前访客当前进口流量",columnType="int")
    private Integer inFlow;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除",defaultValue="'0'",upsertStrategy = LazyFieldStrategy.NEVER,columnType="tinyint")
    private Boolean isDeleted;

    /**
     * 
     * 当前访客出口流量
     */
    @Schema(description ="当前访客出口流量",name ="outFlow",example = "")
    @LazyTableField(name="out_flow",comment="当前访客出口流量",columnType="int")
    private Integer outFlow;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;

    /**
     * 
     * 访客端口
     */
    @Schema(description ="访客端口",name ="visitorPort",example = "")
    @LazyTableFieldUnique(name="visitor_port",comment="访客端口",columnType="int")
    private Integer visitorPort;

}