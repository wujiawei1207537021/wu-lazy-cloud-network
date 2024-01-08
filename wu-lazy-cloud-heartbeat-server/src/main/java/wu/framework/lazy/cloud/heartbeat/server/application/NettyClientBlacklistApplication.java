package wu.framework.lazy.on.cloud.heartbeat.server.domain.application;

import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.command.netty.client.blacklist.*;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.dto.NettyClientBlacklistDTO;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.model.netty.client.blacklist.NettyClientBlacklist;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 客户端黑名单 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface NettyClientBlacklistApplication {


    /**
     * describe 新增客户端黑名单
     *
     * @param nettyClientBlacklistStoryCommand 新增客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklist> story(NettyClientBlacklistStoryCommand nettyClientBlacklistStoryCommand);

    /**
     * describe 批量新增客户端黑名单
     *
     * @param nettyClientBlacklistStoryCommandList 批量新增客户端黑名单     
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientBlacklist>> batchStory(List<NettyClientBlacklistStoryCommand> nettyClientBlacklistStoryCommandList);

    /**
     * describe 更新客户端黑名单
     *
     * @param nettyClientBlacklistUpdateCommand 更新客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklist> updateOne(NettyClientBlacklistUpdateCommand nettyClientBlacklistUpdateCommand);

    /**
     * describe 查询单个客户端黑名单
     *
     * @param nettyClientBlacklistQueryOneCommand 查询单个客户端黑名单     
     * @return {@link Result< NettyClientBlacklistDTO >} 客户端黑名单DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklistDTO> findOne(NettyClientBlacklistQueryOneCommand nettyClientBlacklistQueryOneCommand);

    /**
     * describe 查询多个客户端黑名单
     *
     * @param nettyClientBlacklistQueryListCommand 查询多个客户端黑名单     
     * @return {@link Result <List<NettyClientBlacklistDTO>>} 客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result <List<NettyClientBlacklistDTO>> findList(NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand);

    /**
     * describe 分页查询多个客户端黑名单
     *
     * @param nettyClientBlacklistQueryListCommand 分页查询多个客户端黑名单     
     * @return {@link Result <LazyPage<NettyClientBlacklistDTO>>} 分页客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result <LazyPage<NettyClientBlacklistDTO>> findPage(int size,int current,NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand);

    /**
     * describe 删除客户端黑名单
     *
     * @param nettyClientBlacklistRemoveCommand 删除客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklist> remove(NettyClientBlacklistRemoveCommand nettyClientBlacklistRemoveCommand);

    /**
     * describe 是否存在客户端黑名单
     *
     * @param nettyClientBlacklist 是否存在客户端黑名单
     * @return {@link Result<Boolean>} 客户端黑名单是否存在

     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<Boolean> exists(NettyClientBlacklist nettyClientBlacklist);
}