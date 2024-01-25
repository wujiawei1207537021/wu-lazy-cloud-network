package wu.framework.lazy.cloud.heartbeat.server.application.assembler;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.blacklist.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientBlacklistDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist.NettyClientBlacklist;

/**
 * describe 客户端黑名单
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler
 **/
@Mapper
public interface NettyClientBlacklistDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklistDTOAssembler INSTANCE = Mappers.getMapper(NettyClientBlacklistDTOAssembler.class);

    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param nettyClientBlacklistStoryCommand 保存客户端黑名单对象
     * @return {@link NettyClientBlacklist} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklist toNettyClientBlacklist(NettyClientBlacklistStoryCommand nettyClientBlacklistStoryCommand);

    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param nettyClientBlacklistUpdateCommand 更新客户端黑名单对象
     * @return {@link NettyClientBlacklist} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklist toNettyClientBlacklist(NettyClientBlacklistUpdateCommand nettyClientBlacklistUpdateCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param nettyClientBlacklistQueryOneCommand 查询单个客户端黑名单对象参数
     * @return {@link NettyClientBlacklist} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklist toNettyClientBlacklist(NettyClientBlacklistQueryOneCommand nettyClientBlacklistQueryOneCommand);

    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param nettyClientBlacklistQueryListCommand 查询集合客户端黑名单对象参数
     * @return {@link NettyClientBlacklist} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklist toNettyClientBlacklist(NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand);

    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param nettyClientBlacklistRemoveCommand 删除客户端黑名单对象参数
     * @return {@link NettyClientBlacklist} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklist toNettyClientBlacklist(NettyClientBlacklistRemoveCommand nettyClientBlacklistRemoveCommand);

    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param nettyClientBlacklist 客户端黑名单领域对象
     * @return {@link NettyClientBlacklistDTO} 客户端黑名单DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklistDTO fromNettyClientBlacklist(NettyClientBlacklist nettyClientBlacklist);
}