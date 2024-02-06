package wu.framework.lazy.cloud.heartbeat.server.controller;

import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import wu.framework.lazy.cloud.heartbeat.server.application.VisitorPortFlowApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorPortFlowDTO;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;

import java.util.List;

/**
 * describe 访客端流量
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "访客端流量提供者")
@EasyController("/visitor/port/flow")
public class VisitorPortFlowProvider {

    @Resource
    private VisitorPortFlowApplication visitorPortFlowApplication;

    /**
     * describe 新增访客端流量
     *
     * @param visitorPortFlowStoryCommand 新增访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "新增访客端流量")
    @PostMapping("/story")
    public Result<VisitorPortFlow> story(@RequestBody VisitorPortFlowStoryCommand visitorPortFlowStoryCommand) {
        return visitorPortFlowApplication.story(visitorPortFlowStoryCommand);
    }

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorPortFlowStoryCommandList 批量新增访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "批量新增访客端流量")
    @PostMapping("/batchStory")
    public Result<List<VisitorPortFlow>> batchStory(@RequestBody List<VisitorPortFlowStoryCommand> visitorPortFlowStoryCommandList) {
        return visitorPortFlowApplication.batchStory(visitorPortFlowStoryCommandList);
    }

    /**
     * describe 更新访客端流量
     *
     * @param visitorPortFlowUpdateCommand 更新访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "更新访客端流量")
    @PutMapping("/updateOne")
    public Result<VisitorPortFlow> updateOne(@RequestBody VisitorPortFlowUpdateCommand visitorPortFlowUpdateCommand) {
        return visitorPortFlowApplication.updateOne(visitorPortFlowUpdateCommand);
    }

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorPortFlowQueryOneCommand 查询单个访客端流量
     * @return {@link Result< VisitorPortFlowDTO >} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "查询单个访客端流量")
    @GetMapping("/findOne")
    public Result<VisitorPortFlowDTO> findOne(@ModelAttribute VisitorPortFlowQueryOneCommand visitorPortFlowQueryOneCommand) {
        return visitorPortFlowApplication.findOne(visitorPortFlowQueryOneCommand);
    }

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorPortFlowQueryListCommand 查询多个访客端流量
     * @return {@link Result<List< VisitorPortFlowDTO >>} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "查询多个访客端流量")
    @GetMapping("/findList")
    public Result<List<VisitorPortFlowDTO>> findList(@ModelAttribute VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand) {
        return visitorPortFlowApplication.findList(visitorPortFlowQueryListCommand);
    }

    /**
     * describe 分页查询多个访客端流量
     *
     * @param visitorPortFlowQueryListCommand 分页查询多个访客端流量
     * @return {@link Result<LazyPage< VisitorPortFlowDTO >>} 分页访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "分页查询多个访客端流量")
    @GetMapping("/findPage")
    public Result<LazyPage<VisitorPortFlowDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                         @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand) {
        return visitorPortFlowApplication.findPage(size, current, visitorPortFlowQueryListCommand);
    }

    /**
     * 根据客户端查询流量
     *
     * @param size                            分页大小
     * @param current                         分页
     * @param visitorPortFlowQueryListCommand 查询条件
     * @return {@link Result<LazyPage< VisitorFlowDTO >>} 分页访客端流量DTO对象
     */
    @Operation(summary = "分页查询多个访客端流量")
    @GetMapping("/findClientFlowPage")
    public Result<LazyPage<VisitorFlowDTO>> findClientFlowPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                               @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand) {
        return visitorPortFlowApplication.findClientFlowPage(size, current, visitorPortFlowQueryListCommand);
    }

    /**
     * describe 删除访客端流量
     *
     * @param visitorPortFlowRemoveCommand 删除访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Operation(summary = "删除访客端流量")
    @DeleteMapping("/remove")
    public Result<VisitorPortFlow> remove(@ModelAttribute VisitorPortFlowRemoveCommand visitorPortFlowRemoveCommand) {
        return visitorPortFlowApplication.remove(visitorPortFlowRemoveCommand);
    }
}