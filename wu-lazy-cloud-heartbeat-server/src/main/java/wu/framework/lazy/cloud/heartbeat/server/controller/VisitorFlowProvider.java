package wu.framework.lazy.cloud.heartbeat.server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import com.wu.framework.inner.layer.web.EasyController;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.Resource;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowRemoveCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowUpdateCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryOneCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.VisitorFlowApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "访客端流量提供者")
@EasyController("/visitor/flow")
public class VisitorFlowProvider  {

    @Resource
    private VisitorFlowApplication visitorFlowApplication;

    /**
     * describe 新增访客端流量
     *
     * @param visitorFlowStoryCommand 新增访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "新增访客端流量")
    @PostMapping("/story")
    public Result<VisitorFlow> story(@RequestBody VisitorFlowStoryCommand visitorFlowStoryCommand){
        return visitorFlowApplication.story(visitorFlowStoryCommand);
    }
    /**
     * describe 批量新增访客端流量
     *
     * @param visitorFlowStoryCommandList 批量新增访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "批量新增访客端流量")
    @PostMapping("/batchStory")
    public Result<List<VisitorFlow>> batchStory(@RequestBody List<VisitorFlowStoryCommand> visitorFlowStoryCommandList){
        return visitorFlowApplication.batchStory(visitorFlowStoryCommandList);
    }
    /**
     * describe 更新访客端流量
     *
     * @param visitorFlowUpdateCommand 更新访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "更新访客端流量")
    @PutMapping("/updateOne")
    public Result<VisitorFlow> updateOne(@RequestBody VisitorFlowUpdateCommand visitorFlowUpdateCommand){
        return visitorFlowApplication.updateOne(visitorFlowUpdateCommand);
    }
    /**
     * describe 查询单个访客端流量
     *
     * @param visitorFlowQueryOneCommand 查询单个访客端流量     
     * @return {@link Result<VisitorFlowDTO>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "查询单个访客端流量")
    @GetMapping("/findOne")
    public Result<VisitorFlowDTO> findOne(@ModelAttribute VisitorFlowQueryOneCommand visitorFlowQueryOneCommand){
        return visitorFlowApplication.findOne(visitorFlowQueryOneCommand);
    }
    /**
     * describe 查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 查询多个访客端流量     
     * @return {@link Result<List<VisitorFlowDTO>>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "查询多个访客端流量")
    @GetMapping("/findList")
    public Result<List<VisitorFlowDTO>> findList(@ModelAttribute VisitorFlowQueryListCommand visitorFlowQueryListCommand){
        return visitorFlowApplication.findList(visitorFlowQueryListCommand);
    }
    /**
     * describe 分页查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 分页查询多个访客端流量     
     * @return {@link Result<LazyPage<VisitorFlowDTO>>} 分页访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "分页查询多个访客端流量")
    @GetMapping("/findPage")
    public Result<LazyPage<VisitorFlowDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute VisitorFlowQueryListCommand visitorFlowQueryListCommand){
        return visitorFlowApplication.findPage(size,current,visitorFlowQueryListCommand);
    }
    /**
     * describe 删除访客端流量
     *
     * @param visitorFlowRemoveCommand 删除访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "删除访客端流量")
    @DeleteMapping("/remove")
    public Result<VisitorFlow> remove(@ModelAttribute VisitorFlowRemoveCommand visitorFlowRemoveCommand){
        return visitorFlowApplication.remove(visitorFlowRemoveCommand);
    }
}