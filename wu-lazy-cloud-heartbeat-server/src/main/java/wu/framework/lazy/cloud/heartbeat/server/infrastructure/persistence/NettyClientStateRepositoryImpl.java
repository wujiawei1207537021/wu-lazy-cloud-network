package wu.framework.lazy.cloud.heartbeat.server.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter.NettyClientStateConverter;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.NettyClientStateDO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientStateRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 客户端状态
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class NettyClientStateRepositoryImpl implements NettyClientStateRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增客户端状态
     *
     * @param nettyClientState 新增客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> story(NettyClientState nettyClientState) {

        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        lazyLambdaStream.upsert(nettyClientStateDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增客户端状态
     *
     * @param nettyClientStateList 批量新增客户端状态
     * @return {@link Result<List<NettyClientState>>} 客户端状态新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientState>> batchStory(List<NettyClientState> nettyClientStateList) {
        List<NettyClientStateDO> nettyClientStateDOList = nettyClientStateList.stream().map(NettyClientStateConverter.INSTANCE::fromNettyClientState).collect(Collectors.toList());
        lazyLambdaStream.upsert(nettyClientStateDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个客户端状态
     *
     * @param nettyClientState 查询单个客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> findOne(NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        NettyClientState nettyClientStateOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(nettyClientStateDO), NettyClientState.class);
        return ResultFactory.successOf(nettyClientStateOne);
    }

    /**
     * describe 查询多个客户端状态
     *
     * @param nettyClientState 查询多个客户端状态
     * @return {@link Result<List<NettyClientState>>} 客户端状态领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientState>> findList(NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        List<NettyClientState> nettyClientStateList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(nettyClientStateDO), NettyClientState.class);
        return ResultFactory.successOf(nettyClientStateList);
    }

    /**
     * describe 分页查询多个客户端状态
     *
     * @param size             当前页数
     * @param current          当前页
     * @param nettyClientState 分页查询多个客户端状态
     * @return {@link Result<LazyPage<NettyClientState>>} 分页客户端状态领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<LazyPage<NettyClientState>> findPage(int size, int current, NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        LazyPage<NettyClientState> lazyPage = new LazyPage<>(current, size);
        LazyPage<NettyClientState> nettyClientStateLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(nettyClientStateDO), lazyPage, NettyClientState.class);
        return ResultFactory.successOf(nettyClientStateLazyPage);
    }

    /**
     * describe 删除客户端状态
     *
     * @param nettyClientState 删除客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> remove(NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(nettyClientStateDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在客户端状态
     *
     * @param nettyClientState 客户端状态领域对象
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<Boolean> exists(NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        Boolean exists = lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(nettyClientStateDO));
        return ResultFactory.successOf(exists);
    }

    /**
     * 修改客户端状态
     *
     * @param nettyClientState 客户端状态
     * @return Result<Void>
     */
    @Override
    public Result<Void> updateOnLIneState(NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        // 查询客户端是否存在
        // 存在更新客户端在线状态
        // 不存在新增一条
        lazyLambdaStream.upsertRemoveNull(nettyClientStateDO);
        return ResultFactory.successOf();
    }

    /**
     * 修改客户端暂存状态
     *
     * @param nettyClientState 客户端信息
     * @return Result<Void>
     */
    @Override
    public Result<Void> updateStagingState(NettyClientState nettyClientState) {
        NettyClientStateDO nettyClientStateDO = NettyClientStateConverter.INSTANCE.fromNettyClientState(nettyClientState);
        // 查询客户端是否存在
        // 存在更新客户端在线状态
        // 不存在新增一条
        lazyLambdaStream.upsertRemoveNull(nettyClientStateDO);
        return ResultFactory.successOf();
    }
}