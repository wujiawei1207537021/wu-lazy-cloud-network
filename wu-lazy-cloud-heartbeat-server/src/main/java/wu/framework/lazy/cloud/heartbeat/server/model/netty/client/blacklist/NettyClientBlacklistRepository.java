package wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 客户端黑名单 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface NettyClientBlacklistRepository {


    /**
     * describe 新增客户端黑名单
     *
     * @param nettyClientBlacklist 新增客户端黑名单     
     * @return {@link  Result<NettyClientBlacklist>} 客户端黑名单新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklist> story(NettyClientBlacklist nettyClientBlacklist);

    /**
     * describe 批量新增客户端黑名单
     *
     * @param nettyClientBlacklistList 批量新增客户端黑名单     
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientBlacklist>> batchStory(List<NettyClientBlacklist> nettyClientBlacklistList);

    /**
     * describe 查询单个客户端黑名单
     *
     * @param nettyClientBlacklist 查询单个客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklist> findOne(NettyClientBlacklist nettyClientBlacklist);

    /**
     * describe 查询多个客户端黑名单
     *
     * @param nettyClientBlacklist 查询多个客户端黑名单     
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientBlacklist>> findList(NettyClientBlacklist nettyClientBlacklist);

    /**
     * describe 分页查询多个客户端黑名单
     *
     * @param size 当前页数
     * @param current 当前页
     * @param nettyClientBlacklist 分页查询多个客户端黑名单     
     * @return {@link Result<LazyPage<NettyClientBlacklist>>} 分页客户端黑名单领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<LazyPage<NettyClientBlacklist>> findPage(int size,int current,NettyClientBlacklist nettyClientBlacklist);

    /**
     * describe 删除客户端黑名单
     *
     * @param nettyClientBlacklist 删除客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientBlacklist> remove(NettyClientBlacklist nettyClientBlacklist);

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