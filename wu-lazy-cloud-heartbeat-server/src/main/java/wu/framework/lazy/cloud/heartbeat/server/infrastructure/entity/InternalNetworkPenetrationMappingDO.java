package wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity;

import com.wu.framework.inner.lazy.stereotype.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureEntity 
 **/
@Data
@Accessors(chain = true)
@LazyTable(tableName = "internal_network_penetration_mapping",schema = "middleground_cloud_netty_server",comment = "内网穿透映射")
@Schema(title = "internal_network_penetration_mapping",description = "内网穿透映射")
public class InternalNetworkPenetrationMappingDO {


    /**
     * 
     * 客户端ID
     */
    @Schema(description ="客户端ID",name ="clientId",example = "")
    @LazyTableField(name="client_id",comment="客户端ID",columnType="varchar(255)")
    private String clientId;

    /**
     * 
     * 客户端目标地址
     */
    @Schema(description ="客户端目标地址",name ="clientTargetIp",example = "")
    @LazyTableField(name="client_target_ip",comment="客户端目标地址",columnType="varchar(255)",defaultValue = "'0.0.0.0'")
    private String clientTargetIp;

    /**
     * 
     * 客户端目标端口
     */
    @Schema(description ="客户端目标端口",name ="clientTargetPort",example = "")
    @LazyTableField(name="client_target_port",comment="客户端目标端口",columnType="int",notNull = true)
    private Integer clientTargetPort;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    @LazyTableFieldCreateTime
    private LocalDateTime createTime;

    /**
     * 
     * 主键自增
     */
    @Schema(description ="主键自增",name ="id",example = "")
    @LazyTableFieldId
    private Long id;

    /**
     * 
     * 是否删除 默认否
     */
    @Schema(description ="是否删除 默认否",name ="isDeleted",example = "")
    @LazyTableField(name="is_deleted",comment="是否删除 默认否",columnType="tinyint",defaultValue = "'0'")
    private Boolean isDeleted;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    @LazyTableFieldUpdateTime
    private LocalDateTime updateTime;

    /**
     * 
     * 访问端口
     */
    @Schema(description ="访问端口",name ="visitorPort",example = "")
    @LazyTableFieldUnique(comment = "访问端口",notNull = true)
    private Integer visitorPort;
    /**
     *
     * 描述
     */
    @Schema(description ="描述",name ="describe",example = "")
    @LazyTableField(comment = "描述")
    private String describe;

}