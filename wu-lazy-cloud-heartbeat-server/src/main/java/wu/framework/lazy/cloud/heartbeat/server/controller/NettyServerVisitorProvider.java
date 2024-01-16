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
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorRemoveCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorUpdateCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorQueryOneCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.NettyServerVisitorApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyServerVisitorDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 服务端提前开放出来的端口 
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController 
 **/
@Tag(name = "服务端提前开放出来的端口提供者")
@EasyController("/netty/server/visitor")
public class NettyServerVisitorProvider  {

    @Resource
    private NettyServerVisitorApplication nettyServerVisitorApplication;

    /**
     * describe 新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorStoryCommand 新增服务端提前开放出来的端口     
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "新增服务端提前开放出来的端口")
    @PostMapping("/story")
    public Result<NettyServerVisitor> story(@RequestBody NettyServerVisitorStoryCommand nettyServerVisitorStoryCommand){
        return nettyServerVisitorApplication.story(nettyServerVisitorStoryCommand);
    }
    /**
     * describe 批量新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorStoryCommandList 批量新增服务端提前开放出来的端口     
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "批量新增服务端提前开放出来的端口")
    @PostMapping("/batchStory")
    public Result<List<NettyServerVisitor>> batchStory(@RequestBody List<NettyServerVisitorStoryCommand> nettyServerVisitorStoryCommandList){
        return nettyServerVisitorApplication.batchStory(nettyServerVisitorStoryCommandList);
    }
    /**
     * describe 更新服务端提前开放出来的端口
     *
     * @param nettyServerVisitorUpdateCommand 更新服务端提前开放出来的端口     
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "更新服务端提前开放出来的端口")
    @PutMapping("/updateOne")
    public Result<NettyServerVisitor> updateOne(@RequestBody NettyServerVisitorUpdateCommand nettyServerVisitorUpdateCommand){
        return nettyServerVisitorApplication.updateOne(nettyServerVisitorUpdateCommand);
    }
    /**
     * describe 查询单个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryOneCommand 查询单个服务端提前开放出来的端口     
     * @return {@link Result<NettyServerVisitorDTO>} 服务端提前开放出来的端口DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "查询单个服务端提前开放出来的端口")
    @GetMapping("/findOne")
    public Result<NettyServerVisitorDTO> findOne(@ModelAttribute NettyServerVisitorQueryOneCommand nettyServerVisitorQueryOneCommand){
        return nettyServerVisitorApplication.findOne(nettyServerVisitorQueryOneCommand);
    }
    /**
     * describe 查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryListCommand 查询多个服务端提前开放出来的端口     
     * @return {@link Result<List<NettyServerVisitorDTO>>} 服务端提前开放出来的端口DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "查询多个服务端提前开放出来的端口")
    @GetMapping("/findList")
    public Result<List<NettyServerVisitorDTO>> findList(@ModelAttribute NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand){
        return nettyServerVisitorApplication.findList(nettyServerVisitorQueryListCommand);
    }
    /**
     * describe 分页查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryListCommand 分页查询多个服务端提前开放出来的端口     
     * @return {@link Result<LazyPage<NettyServerVisitorDTO>>} 分页服务端提前开放出来的端口DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "分页查询多个服务端提前开放出来的端口")
    @GetMapping("/findPage")
    public Result<LazyPage<NettyServerVisitorDTO>> findPage(@Parameter(description ="分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                           @Parameter(description ="当前页数") @RequestParam(defaultValue = "1", value = "current") int current,@ModelAttribute NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand){
        return nettyServerVisitorApplication.findPage(size,current,nettyServerVisitorQueryListCommand);
    }
    /**
     * describe 删除服务端提前开放出来的端口
     *
     * @param nettyServerVisitorRemoveCommand 删除服务端提前开放出来的端口     
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Operation(summary = "删除服务端提前开放出来的端口")
    @DeleteMapping("/remove")
    public Result<NettyServerVisitor> remove(@ModelAttribute NettyServerVisitorRemoveCommand nettyServerVisitorRemoveCommand){
        return nettyServerVisitorApplication.remove(nettyServerVisitorRemoveCommand);
    }
}