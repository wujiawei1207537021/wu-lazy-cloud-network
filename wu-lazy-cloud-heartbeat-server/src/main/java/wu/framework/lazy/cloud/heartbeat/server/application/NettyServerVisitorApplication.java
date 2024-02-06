package wu.framework.lazy.cloud.heartbeat.server.application;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyServerVisitorDTO;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;

import java.util.List;

/**
 * describe 服务端提前开放出来的端口
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface NettyServerVisitorApplication {


    /**
     * describe 新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorStoryCommand 新增服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitor> story(NettyServerVisitorStoryCommand nettyServerVisitorStoryCommand);

    /**
     * describe 批量新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorStoryCommandList 批量新增服务端提前开放出来的端口
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<List<NettyServerVisitor>> batchStory(List<NettyServerVisitorStoryCommand> nettyServerVisitorStoryCommandList);

    /**
     * describe 更新服务端提前开放出来的端口
     *
     * @param nettyServerVisitorUpdateCommand 更新服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitor> updateOne(NettyServerVisitorUpdateCommand nettyServerVisitorUpdateCommand);

    /**
     * describe 查询单个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryOneCommand 查询单个服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitorDTO>} 服务端提前开放出来的端口DTO对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitorDTO> findOne(NettyServerVisitorQueryOneCommand nettyServerVisitorQueryOneCommand);

    /**
     * describe 查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryListCommand 查询多个服务端提前开放出来的端口
     * @return {@link Result <List<NettyServerVisitorDTO>>} 服务端提前开放出来的端口DTO对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<List<NettyServerVisitorDTO>> findList(NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand);

    /**
     * describe 分页查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitorQueryListCommand 分页查询多个服务端提前开放出来的端口
     * @return {@link Result <LazyPage<NettyServerVisitorDTO>>} 分页服务端提前开放出来的端口DTO对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<LazyPage<NettyServerVisitorDTO>> findPage(int size, int current, NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand);

    /**
     * describe 删除服务端提前开放出来的端口
     *
     * @param nettyServerVisitorRemoveCommand 删除服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitor> remove(NettyServerVisitorRemoveCommand nettyServerVisitorRemoveCommand);

}