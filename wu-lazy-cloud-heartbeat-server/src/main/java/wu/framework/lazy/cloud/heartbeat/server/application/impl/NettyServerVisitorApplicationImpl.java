package wu.framework.lazy.cloud.heartbeat.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import jakarta.annotation.Resource;
import wu.framework.lazy.cloud.heartbeat.server.application.NettyServerVisitorApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.assembler.NettyServerVisitorDTOAssembler;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyServerVisitorDTO;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitorRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 服务端提前开放出来的端口
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class NettyServerVisitorApplicationImpl implements NettyServerVisitorApplication {

    @Resource
    NettyServerVisitorRepository nettyServerVisitorRepository;

    /**
     * describe 新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorStoryCommand 新增服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitor> story(NettyServerVisitorStoryCommand nettyServerVisitorStoryCommand) {
        NettyServerVisitor nettyServerVisitor = NettyServerVisitorDTOAssembler.INSTANCE.toNettyServerVisitor(nettyServerVisitorStoryCommand);
        return nettyServerVisitorRepository.story(nettyServerVisitor);
    }

    /**
     * describe 批量新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorStoryCommandList 批量新增服务端提前开放出来的端口
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<List<NettyServerVisitor>> batchStory(List<NettyServerVisitorStoryCommand> nettyServerVisitorStoryCommandList) {
        List<NettyServerVisitor> nettyServerVisitorList = nettyServerVisitorStoryCommandList.stream().map(NettyServerVisitorDTOAssembler.INSTANCE::toNettyServerVisitor).collect(Collectors.toList());
        return nettyServerVisitorRepository.batchStory(nettyServerVisitorList);
    }

    /**
     * describe 更新服务端提前开放出来的端口
     *
     * @param nettyServerVisitorUpdateCommand 更新服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitor> updateOne(NettyServerVisitorUpdateCommand nettyServerVisitorUpdateCommand) {
        NettyServerVisitor nettyServerVisitor = NettyServerVisitorDTOAssembler.INSTANCE.toNettyServerVisitor(nettyServerVisitorUpdateCommand);
        return nettyServerVisitorRepository.story(nettyServerVisitor);
    }

    /**
     * describe 查询单个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryOneCommand 查询单个服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitorDTO>} 服务端提前开放出来的端口DTO对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitorDTO> findOne(NettyServerVisitorQueryOneCommand nettyServerVisitorQueryOneCommand) {
        NettyServerVisitor nettyServerVisitor = NettyServerVisitorDTOAssembler.INSTANCE.toNettyServerVisitor(nettyServerVisitorQueryOneCommand);
        return nettyServerVisitorRepository.findOne(nettyServerVisitor).convert(NettyServerVisitorDTOAssembler.INSTANCE::fromNettyServerVisitor);
    }

    /**
     * describe 查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryListCommand 查询多个服务端提前开放出来的端口
     * @return {@link Result<List<NettyServerVisitorDTO>>} 服务端提前开放出来的端口DTO对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<List<NettyServerVisitorDTO>> findList(NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand) {
        NettyServerVisitor nettyServerVisitor = NettyServerVisitorDTOAssembler.INSTANCE.toNettyServerVisitor(nettyServerVisitorQueryListCommand);
        return nettyServerVisitorRepository.findList(nettyServerVisitor).convert(nettyServerVisitors -> nettyServerVisitors.stream().map(NettyServerVisitorDTOAssembler.INSTANCE::fromNettyServerVisitor).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryListCommand 分页查询多个服务端提前开放出来的端口
     * @return {@link Result<LazyPage<NettyServerVisitorDTO>>} 分页服务端提前开放出来的端口DTO对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<LazyPage<NettyServerVisitorDTO>> findPage(int size, int current, NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand) {
        NettyServerVisitor nettyServerVisitor = NettyServerVisitorDTOAssembler.INSTANCE.toNettyServerVisitor(nettyServerVisitorQueryListCommand);
        return nettyServerVisitorRepository.findPage(size, current, nettyServerVisitor).convert(page -> page.convert(NettyServerVisitorDTOAssembler.INSTANCE::fromNettyServerVisitor));
    }

    /**
     * describe 删除服务端提前开放出来的端口
     *
     * @param nettyServerVisitorRemoveCommand 删除服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitor> remove(NettyServerVisitorRemoveCommand nettyServerVisitorRemoveCommand) {
        NettyServerVisitor nettyServerVisitor = NettyServerVisitorDTOAssembler.INSTANCE.toNettyServerVisitor(nettyServerVisitorRemoveCommand);
        return nettyServerVisitorRepository.remove(nettyServerVisitor);
    }

}