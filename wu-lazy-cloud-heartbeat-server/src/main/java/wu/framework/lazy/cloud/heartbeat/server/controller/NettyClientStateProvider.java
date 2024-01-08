package wu.framework.lazy.cloud.heartbeat.server.controller;


import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import wu.framework.lazy.cloud.heartbeat.server.application.NettyClientStateApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientStateDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;

import java.util.List;

/**
 * describe 客户端状态 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "客户端状态提供者")
@EasyController("/netty/client/state")
public class NettyClientStateProvider  {

    @Resource
    private NettyClientStateApplication nettyClientStateApplication;

    /**
     * describe 新增客户端状态
     *
     * @param nettyClientStateStoryCommand 新增客户端状态     
     * @return {@link Result< NettyClientState >} 客户端状态新增后领域对象
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "新增客户端状态")
    @PostMapping("/story")
    public Result<NettyClientState> story(@RequestBody NettyClientStateStoryCommand nettyClientStateStoryCommand){
        return nettyClientStateApplication.story(nettyClientStateStoryCommand);
    }
    /**
     * describe 批量新增客户端状态
     *
     * @param nettyClientStateStoryCommandList 批量新增客户端状态     
     * @return {@link Result <List<NettyClientState>>} 客户端状态新增后领域对象集合
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "批量新增客户端状态")
    @PostMapping("/batchStory")
    public Result<List<NettyClientState>> batchStory(@RequestBody List<NettyClientStateStoryCommand> nettyClientStateStoryCommandList){
        return nettyClientStateApplication.batchStory(nettyClientStateStoryCommandList);
    }
    /**
     * describe 更新客户端状态
     *
     * @param nettyClientStateUpdateCommand 更新客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "更新客户端状态")
    @PutMapping("/updateOne")
    public Result<NettyClientState> updateOne(@RequestBody NettyClientStateUpdateCommand nettyClientStateUpdateCommand){
        return nettyClientStateApplication.updateOne(nettyClientStateUpdateCommand);
    }
    /**
     * describe 查询单个客户端状态
     *
     * @param nettyClientStateQueryOneCommand 查询单个客户端状态     
     * @return {@link Result< NettyClientStateDTO >} 客户端状态DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "查询单个客户端状态")
    @GetMapping("/findOne")
    public Result<NettyClientStateDTO> findOne(@ModelAttribute NettyClientStateQueryOneCommand nettyClientStateQueryOneCommand){
        return nettyClientStateApplication.findOne(nettyClientStateQueryOneCommand);
    }
    /**
     * describe 查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 查询多个客户端状态     
     * @return {@link Result<List<NettyClientStateDTO>>} 客户端状态DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "查询多个客户端状态")
    @GetMapping("/findList")
    public Result<List<NettyClientStateDTO>> findList(@ModelAttribute NettyClientStateQueryListCommand nettyClientStateQueryListCommand){
        return nettyClientStateApplication.findList(nettyClientStateQueryListCommand);
    }
    /**
     * describe 分页查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 分页查询多个客户端状态     
     * @return {@link Result< LazyPage <NettyClientStateDTO>>} 分页客户端状态DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "分页查询多个客户端状态")
    @GetMapping("/findPage")
    public Result<LazyPage<NettyClientStateDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                          @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute NettyClientStateQueryListCommand nettyClientStateQueryListCommand){
        return nettyClientStateApplication.findPage(size,current,nettyClientStateQueryListCommand);
    }
    /**
     * describe 删除客户端状态
     *
     * @param nettyClientStateRemoveCommand 删除客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "删除客户端状态")
    @DeleteMapping("/remove")
    public Result<NettyClientState> remove(@ModelAttribute NettyClientStateRemoveCommand nettyClientStateRemoveCommand){
        return nettyClientStateApplication.remove(nettyClientStateRemoveCommand);
    }
}