package wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.blacklist;


import com.wu.framework.response.mark.ValidType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
/**
 * describe 客户端黑名单 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyStoryCommand 
 **/
@Data
@Accessors(chain = true)
@Schema(title = "netty_client_blacklist_story_command",description = "客户端黑名单")
public class NettyClientBlacklistStoryCommand {


    /**
     * 
     * 客户端ID
     */
    @NotNull(groups = ValidType.Create.class,message = "客户端ID不允许为空")
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

}