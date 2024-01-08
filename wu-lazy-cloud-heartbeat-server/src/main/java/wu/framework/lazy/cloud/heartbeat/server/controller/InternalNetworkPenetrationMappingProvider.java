package wu.framework.lazy.on.cloud.heartbeat.server.domain.controller;


import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.InternalNetworkPenetrationMappingApplication;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.command.internal.network.penetration.mapping.*;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.dto.InternalNetworkPenetrationMappingDTO;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.model.internal.network.penetration.mapping.InternalNetworkPenetrationMapping;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 内网穿透映射
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "内网穿透映射提供者")
@EasyController("/internal/network/penetration/mapping")
public class InternalNetworkPenetrationMappingProvider {

    @Resource
    private InternalNetworkPenetrationMappingApplication internalNetworkPenetrationMappingApplication;

    /**
     * describe 新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingStoryCommand 新增内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "新增内网穿透映射")
    @PostMapping("/story")
    public Result<InternalNetworkPenetrationMapping> story(@RequestBody InternalNetworkPenetrationMappingStoryCommand internalNetworkPenetrationMappingStoryCommand) {
        return internalNetworkPenetrationMappingApplication.story(internalNetworkPenetrationMappingStoryCommand);
    }

    /**
     * describe 批量新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingStoryCommandList 批量新增内网穿透映射
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "批量新增内网穿透映射")
    @PostMapping("/batchStory")
    public Result<List<InternalNetworkPenetrationMapping>> batchStory(@RequestBody List<InternalNetworkPenetrationMappingStoryCommand> internalNetworkPenetrationMappingStoryCommandList) {
        return internalNetworkPenetrationMappingApplication.batchStory(internalNetworkPenetrationMappingStoryCommandList);
    }

    /**
     * describe 更新内网穿透映射
     *
     * @param internalNetworkPenetrationMappingUpdateCommand 更新内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射领域对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "更新内网穿透映射")
    @PutMapping("/updateOne")
    public Result<InternalNetworkPenetrationMapping> updateOne(@RequestBody InternalNetworkPenetrationMappingUpdateCommand internalNetworkPenetrationMappingUpdateCommand) {
        return internalNetworkPenetrationMappingApplication.updateOne(internalNetworkPenetrationMappingUpdateCommand);
    }

    /**
     * describe 查询单个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryOneCommand 查询单个内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMappingDTO>} 内网穿透映射DTO对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "查询单个内网穿透映射")
    @GetMapping("/findOne")
    public Result<InternalNetworkPenetrationMappingDTO> findOne(@ModelAttribute InternalNetworkPenetrationMappingQueryOneCommand internalNetworkPenetrationMappingQueryOneCommand) {
        return internalNetworkPenetrationMappingApplication.findOne(internalNetworkPenetrationMappingQueryOneCommand);
    }

    /**
     * describe 查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 查询多个内网穿透映射
     * @return {@link Result<List<InternalNetworkPenetrationMappingDTO>>} 内网穿透映射DTO对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "查询多个内网穿透映射")
    @GetMapping("/findList")
    public Result<List<InternalNetworkPenetrationMappingDTO>> findList(@ModelAttribute InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand) {
        return internalNetworkPenetrationMappingApplication.findList(internalNetworkPenetrationMappingQueryListCommand);
    }

    /**
     * describe 分页查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 分页查询多个内网穿透映射
     * @return {@link Result<LazyPage<InternalNetworkPenetrationMappingDTO>>} 分页内网穿透映射DTO对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "分页查询多个内网穿透映射")
    @GetMapping("/findPage")
    public Result<LazyPage<InternalNetworkPenetrationMappingDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                                           @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand) {
        return internalNetworkPenetrationMappingApplication.findPage(size, current, internalNetworkPenetrationMappingQueryListCommand);
    }

    /**
     * describe 删除内网穿透映射
     *
     * @param internalNetworkPenetrationMappingRemoveCommand 删除内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Operation(summary = "删除内网穿透映射")
    @DeleteMapping("/remove")
    public Result<InternalNetworkPenetrationMapping> remove(@ModelAttribute InternalNetworkPenetrationMappingRemoveCommand internalNetworkPenetrationMappingRemoveCommand) {
        return internalNetworkPenetrationMappingApplication.remove(internalNetworkPenetrationMappingRemoveCommand);
    }
}