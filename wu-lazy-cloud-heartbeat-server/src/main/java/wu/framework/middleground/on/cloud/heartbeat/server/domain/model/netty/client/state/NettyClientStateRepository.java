package wu.framework.middleground.on.cloud.heartbeat.server.domain.model.netty.client.state;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 客户端状态 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface NettyClientStateRepository {


    /**
     * describe 新增客户端状态
     *
     * @param nettyClientState 新增客户端状态     
     * @return {@link  Result<NettyClientState>} 客户端状态新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientState> story(NettyClientState nettyClientState);

    /**
     * describe 批量新增客户端状态
     *
     * @param nettyClientStateList 批量新增客户端状态     
     * @return {@link Result<List<NettyClientState>>} 客户端状态新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientState>> batchStory(List<NettyClientState> nettyClientStateList);

    /**
     * describe 查询单个客户端状态
     *
     * @param nettyClientState 查询单个客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientState> findOne(NettyClientState nettyClientState);

    /**
     * describe 查询多个客户端状态
     *
     * @param nettyClientState 查询多个客户端状态     
     * @return {@link Result<List<NettyClientState>>} 客户端状态DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<List<NettyClientState>> findList(NettyClientState nettyClientState);

    /**
     * describe 分页查询多个客户端状态
     *
     * @param size 当前页数
     * @param current 当前页
     * @param nettyClientState 分页查询多个客户端状态     
     * @return {@link Result<LazyPage<NettyClientState>>} 分页客户端状态领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<LazyPage<NettyClientState>> findPage(int size,int current,NettyClientState nettyClientState);

    /**
     * describe 删除客户端状态
     *
     * @param nettyClientState 删除客户端状态     
     * @return {@link Result<NettyClientState>} 客户端状态     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<NettyClientState> remove(NettyClientState nettyClientState);

    /**
     * describe 是否存在客户端状态
     *
     * @param nettyClientState 是否存在客户端状态     
     * @return {@link Result<Boolean>} 客户端状态是否存在     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    Result<Boolean> exists(NettyClientState nettyClientState);

    /**
     * 修改客户端在线状态
     *
     * @param nettyClientState 客户端状态
     * @return Result<Void>
     */
    Result<Void> updateOnLIneState(NettyClientState nettyClientState);

    /**
     * 修改客户端暂存状态
     * @param nettyClientState 客户端信息
     * @return Result<Void>
     */
    Result<Void> updateStagingState(NettyClientState nettyClientState);
}