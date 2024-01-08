package wu.framework.lazy.on.cloud.heartbeat.server.domain.application.command.internal.network.penetration.mapping;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyRemoveCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "internal_network_penetration_mapping_remove_command",description = "内网穿透映射")
public class InternalNetworkPenetrationMappingRemoveCommand {


    /**
     * 
     * 客户端ID
     */
    @Schema(description ="客户端ID",name ="clientId",example = "")
    private String clientId;

    /**
     * 
     * 客户端目标地址
     */
    @Schema(description ="客户端目标地址",name ="clientTargetIp",example = "")
    private String clientTargetIp;

    /**
     * 
     * 客户端目标端口
     */
    @Schema(description ="客户端目标端口",name ="clientTargetPort",example = "")
    private Integer clientTargetPort;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 主键自增
     */
    @Schema(description ="主键自增",name ="id",example = "")
    private Long id;

    /**
     * 
     * 是否删除 默认否
     */
    @Schema(description ="是否删除 默认否",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 访问端口
     */
    @Schema(description ="访问端口",name ="visitorPort",example = "")
    private Integer visitorPort;
    /**
     *
     * 描述
     */
    @Schema(description ="描述",name ="describe",example = "")
    private String describe;
}