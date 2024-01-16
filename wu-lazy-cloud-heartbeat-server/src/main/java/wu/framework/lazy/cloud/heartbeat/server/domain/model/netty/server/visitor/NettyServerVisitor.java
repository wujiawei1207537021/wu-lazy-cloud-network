package wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.lang.String;
import java.lang.Long;
import java.lang.Boolean;
import java.lang.Integer;
/**
 * describe 服务端提前开放出来的端口 
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomain 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "netty_server_visitor",description = "服务端提前开放出来的端口")
public class NettyServerVisitor {


    /**
     * 
     * 创建时间
     */
    @Schema(description ="创建时间",name ="createTime",example = "")
    private LocalDateTime createTime;

    /**
     * 
     * 描述
     */
    @Schema(description ="描述",name ="describe",example = "")
    private String describe;

    /**
     * 
     * 用户ID
     */
    @Schema(description ="用户ID",name ="id",example = "")
    private Long id;

    /**
     * 
     * 是否删除
     */
    @Schema(description ="是否删除",name ="isDeleted",example = "")
    private Boolean isDeleted;

    /**
     * 
     * 更新时间
     */
    @Schema(description ="更新时间",name ="updateTime",example = "")
    private LocalDateTime updateTime;

    /**
     * 
     * 访客端口
     */
    @Schema(description ="访客端口",name ="visitorPort",example = "")
    private Integer visitorPort;

}