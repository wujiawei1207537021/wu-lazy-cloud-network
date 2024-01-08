package wu.framework.lazy.cloud.heartbeat.server.application.impl;



import wu.framework.lazy.cloud.heartbeat.server.application.NettyClientStateApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.assembler.NettyClientStateDTOAssembler;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state.NettyClientStateStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientStateDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientStateRepository;
import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import jakarta.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;
/**
 * describe 客户端状态 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class NettyClientStateApplicationImpl implements NettyClientStateApplication {

    @Resource
    NettyClientStateRepository nettyClientStateRepository;
    /**
     * describe 新增客户端状态
     *
     * @param nettyClientStateStoryCommand 新增客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> story(NettyClientStateStoryCommand nettyClientStateStoryCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateStoryCommand);
        return nettyClientStateRepository.story(nettyClientState);
    }
    /**
     * describe 批量新增客户端状态
     *
     * @param nettyClientStateStoryCommandList 批量新增客户端状态     
     * @return {@link Result<List<NettyClientState>>} 客户端状态新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientState>> batchStory(List<NettyClientStateStoryCommand> nettyClientStateStoryCommandList) {
        List<NettyClientState> nettyClientStateList = nettyClientStateStoryCommandList.stream().map( NettyClientStateDTOAssembler.INSTANCE::toNettyClientState).collect(Collectors.toList());
        return nettyClientStateRepository.batchStory(nettyClientStateList);
    }
    /**
     * describe 更新客户端状态
     *
     * @param nettyClientStateUpdateCommand 更新客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> updateOne(NettyClientStateUpdateCommand nettyClientStateUpdateCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateUpdateCommand);
        return nettyClientStateRepository.story(nettyClientState);
    }

    /**
     * describe 查询单个客户端状态
     *
     * @param nettyClientStateQueryOneCommand 查询单个客户端状态     
     * @return {@link Result<NettyClientStateDTO>} 客户端状态DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientStateDTO> findOne(NettyClientStateQueryOneCommand nettyClientStateQueryOneCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateQueryOneCommand);
        return nettyClientStateRepository.findOne(nettyClientState).convert(NettyClientStateDTOAssembler.INSTANCE::fromNettyClientState);
    }

    /**
     * describe 查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 查询多个客户端状态     
     * @return {@link Result<List<NettyClientStateDTO>>} 客户端状态DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientStateDTO>> findList(NettyClientStateQueryListCommand nettyClientStateQueryListCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateQueryListCommand);
        return nettyClientStateRepository.findList(nettyClientState)        .convert(nettyClientStates -> nettyClientStates.stream().map(NettyClientStateDTOAssembler.INSTANCE::fromNettyClientState).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 分页查询多个客户端状态     
     * @return {@link Result<LazyPage<NettyClientStateDTO>>} 分页客户端状态DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<LazyPage<NettyClientStateDTO>> findPage(int size,int current,NettyClientStateQueryListCommand nettyClientStateQueryListCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateQueryListCommand);
        return nettyClientStateRepository.findPage(size,current,nettyClientState)        .convert(page -> page.convert(NettyClientStateDTOAssembler.INSTANCE::fromNettyClientState))            ;
    }

    /**
     * describe 删除客户端状态
     *
     * @param nettyClientStateRemoveCommand 删除客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> remove(NettyClientStateRemoveCommand nettyClientStateRemoveCommand) {
     NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateRemoveCommand);
     return nettyClientStateRepository.remove(nettyClientState);
    }

}