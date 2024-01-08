package wu.framework.lazy.cloud.heartbeat.server.application.assembler;

import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientStateDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * describe 客户端状态 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface NettyClientStateDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientStateDTOAssembler INSTANCE = Mappers.getMapper(NettyClientStateDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param nettyClientStateStoryCommand 保存客户端状态对象     
     * @return {@link NettyClientState} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
     NettyClientState toNettyClientState(NettyClientStateStoryCommand nettyClientStateStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param nettyClientStateUpdateCommand 更新客户端状态对象     
     * @return {@link NettyClientState} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
     NettyClientState toNettyClientState(NettyClientStateUpdateCommand nettyClientStateUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param nettyClientStateQueryOneCommand 查询单个客户端状态对象参数     
     * @return {@link NettyClientState} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
     NettyClientState toNettyClientState(NettyClientStateQueryOneCommand nettyClientStateQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param nettyClientStateQueryListCommand 查询集合客户端状态对象参数     
     * @return {@link NettyClientState} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
     NettyClientState toNettyClientState(NettyClientStateQueryListCommand nettyClientStateQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param nettyClientStateRemoveCommand 删除客户端状态对象参数     
     * @return {@link NettyClientState} 客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
     NettyClientState toNettyClientState(NettyClientStateRemoveCommand nettyClientStateRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param nettyClientState 客户端状态领域对象     
     * @return {@link NettyClientStateDTO} 客户端状态DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
     NettyClientStateDTO fromNettyClientState(NettyClientState nettyClientState);
}