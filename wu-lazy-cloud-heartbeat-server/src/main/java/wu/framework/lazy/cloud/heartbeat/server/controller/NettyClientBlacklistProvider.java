package wu.framework.lazy.cloud.heartbeat.server.controller;


import wu.framework.lazy.cloud.heartbeat.server.application.NettyClientBlacklistApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.blacklist.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientBlacklistDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist.NettyClientBlacklist;
import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.mark.ValidType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * describe 客户端黑名单
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyController
 **/
@Tag(name = "客户端黑名单提供者")
@EasyController("/netty/client/blacklist")
public class NettyClientBlacklistProvider {

    @Resource
    private NettyClientBlacklistApplication nettyClientBlacklistApplication;

    /**
     * describe 新增客户端黑名单
     *
     * @param nettyClientBlacklistStoryCommand 新增客户端黑名单
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "新增客户端黑名单")
    @PostMapping("/story")
    public Result<NettyClientBlacklist> story(@Validated(ValidType.Create.class) @RequestBody NettyClientBlacklistStoryCommand nettyClientBlacklistStoryCommand) {
        return nettyClientBlacklistApplication.story(nettyClientBlacklistStoryCommand);
    }

    /**
     * describe 批量新增客户端黑名单
     *
     * @param nettyClientBlacklistStoryCommandList 批量新增客户端黑名单
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "批量新增客户端黑名单")
    @PostMapping("/batchStory")
    public Result<List<NettyClientBlacklist>> batchStory(@Validated(ValidType.Create.class) @RequestBody List<NettyClientBlacklistStoryCommand> nettyClientBlacklistStoryCommandList) {
        return nettyClientBlacklistApplication.batchStory(nettyClientBlacklistStoryCommandList);
    }

    /**
     * describe 更新客户端黑名单
     *
     * @param nettyClientBlacklistUpdateCommand 更新客户端黑名单
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "更新客户端黑名单")
    @PutMapping("/updateOne")
    public Result<NettyClientBlacklist> updateOne(@RequestBody NettyClientBlacklistUpdateCommand nettyClientBlacklistUpdateCommand) {
        return nettyClientBlacklistApplication.updateOne(nettyClientBlacklistUpdateCommand);
    }

    /**
     * describe 查询单个客户端黑名单
     *
     * @param nettyClientBlacklistQueryOneCommand 查询单个客户端黑名单
     * @return {@link Result< NettyClientBlacklistDTO >} 客户端黑名单DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "查询单个客户端黑名单")
    @GetMapping("/findOne")
    public Result<NettyClientBlacklistDTO> findOne(@ModelAttribute NettyClientBlacklistQueryOneCommand nettyClientBlacklistQueryOneCommand) {
        return nettyClientBlacklistApplication.findOne(nettyClientBlacklistQueryOneCommand);
    }

    /**
     * describe 查询多个客户端黑名单
     *
     * @param nettyClientBlacklistQueryListCommand 查询多个客户端黑名单
     * @return {@link Result<List<NettyClientBlacklistDTO>>} 客户端黑名单DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "查询多个客户端黑名单")
    @GetMapping("/findList")
    public Result<List<NettyClientBlacklistDTO>> findList(@ModelAttribute NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand) {
        return nettyClientBlacklistApplication.findList(nettyClientBlacklistQueryListCommand);
    }

    /**
     * describe 分页查询多个客户端黑名单
     *
     * @param nettyClientBlacklistQueryListCommand 分页查询多个客户端黑名单
     * @return {@link Result<LazyPage<NettyClientBlacklistDTO>>} 分页客户端黑名单DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "分页查询多个客户端黑名单")
    @GetMapping("/findPage")
    public Result<LazyPage<NettyClientBlacklistDTO>> findPage(@Parameter(description = "分页大小") @RequestParam(defaultValue = "10", value = "size") int size,
                                                              @Parameter(description = "当前页数") @RequestParam(defaultValue = "1", value = "current") int current, @ModelAttribute NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand) {
        return nettyClientBlacklistApplication.findPage(size, current, nettyClientBlacklistQueryListCommand);
    }

    /**
     * describe 删除客户端黑名单
     *
     * @param nettyClientBlacklistRemoveCommand 删除客户端黑名单
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Operation(summary = "删除客户端黑名单")
    @DeleteMapping("/remove")
    public Result<NettyClientBlacklist> remove(@ModelAttribute NettyClientBlacklistRemoveCommand nettyClientBlacklistRemoveCommand) {
        return nettyClientBlacklistApplication.remove(nettyClientBlacklistRemoveCommand);
    }
}