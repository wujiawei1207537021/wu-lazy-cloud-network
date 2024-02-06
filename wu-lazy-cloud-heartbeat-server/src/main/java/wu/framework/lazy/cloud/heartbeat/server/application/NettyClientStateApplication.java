package wu.framework.lazy.cloud.heartbeat.server.application;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientStateDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;

import java.util.List;

/**
 * describe 客户端状态
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication
 **/

public interface NettyClientStateApplication {


    /**
     * describe 新增客户端状态
     *
     * @param nettyClientStateStoryCommand 新增客户端状态
     * @return {@link Result< NettyClientState >} 客户端状态新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientState> story(NettyClientStateStoryCommand nettyClientStateStoryCommand);

    /**
     * describe 批量新增客户端状态
     *
     * @param nettyClientStateStoryCommandList 批量新增客户端状态
     * @return {@link Result<List<NettyClientState>>} 客户端状态新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientState>> batchStory(List<NettyClientStateStoryCommand> nettyClientStateStoryCommandList);

    /**
     * describe 更新客户端状态
     *
     * @param nettyClientStateUpdateCommand 更新客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientState> updateOne(NettyClientStateUpdateCommand nettyClientStateUpdateCommand);

    /**
     * describe 查询单个客户端状态
     *
     * @param nettyClientStateQueryOneCommand 查询单个客户端状态
     * @return {@link Result< NettyClientStateDTO >} 客户端状态DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientStateDTO> findOne(NettyClientStateQueryOneCommand nettyClientStateQueryOneCommand);

    /**
     * describe 查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 查询多个客户端状态
     * @return {@link Result <List<NettyClientStateDTO>>} 客户端状态DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientStateDTO>> findList(NettyClientStateQueryListCommand nettyClientStateQueryListCommand);

    /**
     * describe 分页查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 分页查询多个客户端状态
     * @return {@link Result <LazyPage<NettyClientStateDTO>>} 分页客户端状态DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<LazyPage<NettyClientStateDTO>> findPage(int size, int current, NettyClientStateQueryListCommand nettyClientStateQueryListCommand);

    /**
     * describe 删除客户端状态
     *
     * @param nettyClientStateRemoveCommand 删除客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientState> remove(NettyClientStateRemoveCommand nettyClientStateRemoveCommand);

    /**
     * 通过客户端心跳通道发送客户端请求
     *
     * @param nettyClientMessageCommand 发送请求到客户端
     * @return {@link Result<Void>}
     */
    Result<Void> sendMessage2HeartbeatClient(NettyClientMessageCommand nettyClientMessageCommand);
}