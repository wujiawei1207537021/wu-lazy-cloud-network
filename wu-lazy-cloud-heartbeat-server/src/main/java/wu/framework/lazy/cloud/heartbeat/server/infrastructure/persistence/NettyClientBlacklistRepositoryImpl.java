package wu.framework.lazy.cloud.heartbeat.server.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter.NettyClientBlacklistConverter;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.NettyClientBlacklistDO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist.NettyClientBlacklist;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist.NettyClientBlacklistRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 客户端黑名单
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class NettyClientBlacklistRepositoryImpl implements NettyClientBlacklistRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增客户端黑名单
     *
     * @param nettyClientBlacklist 新增客户端黑名单
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklist> story(NettyClientBlacklist nettyClientBlacklist) {
        NettyClientBlacklistDO nettyClientBlacklistDO = NettyClientBlacklistConverter.INSTANCE.fromNettyClientBlacklist(nettyClientBlacklist);
        lazyLambdaStream.upsert(nettyClientBlacklistDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增客户端黑名单
     *
     * @param nettyClientBlacklistList 批量新增客户端黑名单
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientBlacklist>> batchStory(List<NettyClientBlacklist> nettyClientBlacklistList) {
        List<NettyClientBlacklistDO> nettyClientBlacklistDOList = nettyClientBlacklistList.stream().map(NettyClientBlacklistConverter.INSTANCE::fromNettyClientBlacklist).collect(Collectors.toList());
        lazyLambdaStream.upsert(nettyClientBlacklistDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个客户端黑名单
     *
     * @param nettyClientBlacklist 查询单个客户端黑名单
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklist> findOne(NettyClientBlacklist nettyClientBlacklist) {
        NettyClientBlacklistDO nettyClientBlacklistDO = NettyClientBlacklistConverter.INSTANCE.fromNettyClientBlacklist(nettyClientBlacklist);
        NettyClientBlacklist nettyClientBlacklistOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(nettyClientBlacklistDO), NettyClientBlacklist.class);
        return ResultFactory.successOf(nettyClientBlacklistOne);
    }

    /**
     * describe 查询多个客户端黑名单
     *
     * @param nettyClientBlacklist 查询多个客户端黑名单
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientBlacklist>> findList(NettyClientBlacklist nettyClientBlacklist) {
        NettyClientBlacklistDO nettyClientBlacklistDO = NettyClientBlacklistConverter.INSTANCE.fromNettyClientBlacklist(nettyClientBlacklist);
        List<NettyClientBlacklist> nettyClientBlacklistList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(nettyClientBlacklistDO), NettyClientBlacklist.class);
        return ResultFactory.successOf(nettyClientBlacklistList);
    }

    /**
     * describe 分页查询多个客户端黑名单
     *
     * @param size                 当前页数
     * @param current              当前页
     * @param nettyClientBlacklist 分页查询多个客户端黑名单
     * @return {@link Result<LazyPage<NettyClientBlacklist>>} 分页客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<LazyPage<NettyClientBlacklist>> findPage(int size, int current, NettyClientBlacklist nettyClientBlacklist) {
        NettyClientBlacklistDO nettyClientBlacklistDO = NettyClientBlacklistConverter.INSTANCE.fromNettyClientBlacklist(nettyClientBlacklist);
        LazyPage<NettyClientBlacklist> lazyPage = new LazyPage<>(current, size);
        LazyPage<NettyClientBlacklist> nettyClientBlacklistLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(nettyClientBlacklistDO), lazyPage, NettyClientBlacklist.class);
        return ResultFactory.successOf(nettyClientBlacklistLazyPage);
    }

    /**
     * describe 删除客户端黑名单
     *
     * @param nettyClientBlacklist 删除客户端黑名单
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklist> remove(NettyClientBlacklist nettyClientBlacklist) {
        NettyClientBlacklistDO nettyClientBlacklistDO = NettyClientBlacklistConverter.INSTANCE.fromNettyClientBlacklist(nettyClientBlacklist);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(nettyClientBlacklistDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在客户端黑名单
     *
     * @param nettyClientBlacklist 客户端黑名单领域对象
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<Boolean> exists(NettyClientBlacklist nettyClientBlacklist) {
        NettyClientBlacklistDO nettyClientBlacklistDO = NettyClientBlacklistConverter.INSTANCE.fromNettyClientBlacklist(nettyClientBlacklist);
        Boolean exists = lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(nettyClientBlacklistDO));
        return ResultFactory.successOf(exists);
    }

}