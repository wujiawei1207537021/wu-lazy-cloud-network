package wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow;

import lombok.Data;
import lombok.experimental.Accessors;
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
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyUpdateCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "visitor_flow_update_command",description = "访客端流量")
public class VisitorFlowUpdateCommand {


    /**
     * 
     * 客户端ID
     */
    @Schema(description ="客户端ID",name ="clientId",example = "")
    private String clientId;

    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 主键
     */
    @Schema(description ="主键",name ="id",example = "")
    private Long id;

    /**
     * 
     * 当前访客当前进口流量
     */
    @Schema(description ="当前访客当前进口流量",name ="inFlow",example = "")
    private Integer inFlow;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 当前访客出口流量
     */
    @Schema(description ="当前访客出口流量",name ="outFlow",example = "")
    private Integer outFlow;

    /**
     * 
     * 修改时间
     */
    @Schema(description ="修改时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 访客端口
     */
    @Schema(description ="访客端口",name ="visitorPort",example = "")
    private Integer visitorPort;

}