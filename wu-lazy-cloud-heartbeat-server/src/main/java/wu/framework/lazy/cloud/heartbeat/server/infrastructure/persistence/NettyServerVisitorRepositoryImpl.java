package wu.framework.lazy.cloud.heartbeat.server.infrastructure.persistence;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitorRepository;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter.NettyServerVisitorConverter;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.NettyServerVisitorDO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 服务端提前开放出来的端口
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class NettyServerVisitorRepositoryImpl implements NettyServerVisitorRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 新增服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitor> story(NettyServerVisitor nettyServerVisitor) {
        NettyServerVisitorDO nettyServerVisitorDO = NettyServerVisitorConverter.INSTANCE.fromNettyServerVisitor(nettyServerVisitor);
        lazyLambdaStream.upsert(nettyServerVisitorDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorList 批量新增服务端提前开放出来的端口
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<List<NettyServerVisitor>> batchStory(List<NettyServerVisitor> nettyServerVisitorList) {
        List<NettyServerVisitorDO> nettyServerVisitorDOList = nettyServerVisitorList.stream().map(NettyServerVisitorConverter.INSTANCE::fromNettyServerVisitor).collect(Collectors.toList());
        lazyLambdaStream.upsert(nettyServerVisitorDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 查询单个服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitor> findOne(NettyServerVisitor nettyServerVisitor) {
        NettyServerVisitorDO nettyServerVisitorDO = NettyServerVisitorConverter.INSTANCE.fromNettyServerVisitor(nettyServerVisitor);
        NettyServerVisitor nettyServerVisitorOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(nettyServerVisitorDO), NettyServerVisitor.class);
        return ResultFactory.successOf(nettyServerVisitorOne);
    }

    /**
     * describe 查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 查询多个服务端提前开放出来的端口
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<List<NettyServerVisitor>> findList(NettyServerVisitor nettyServerVisitor) {
        NettyServerVisitorDO nettyServerVisitorDO = NettyServerVisitorConverter.INSTANCE.fromNettyServerVisitor(nettyServerVisitor);
        List<NettyServerVisitor> nettyServerVisitorList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(nettyServerVisitorDO), NettyServerVisitor.class);
        return ResultFactory.successOf(nettyServerVisitorList);
    }

    /**
     * describe 分页查询多个服务端提前开放出来的端口
     *
     * @param size               当前页数
     * @param current            当前页
     * @param nettyServerVisitor 分页查询多个服务端提前开放出来的端口
     * @return {@link Result<LazyPage<NettyServerVisitor>>} 分页服务端提前开放出来的端口领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<LazyPage<NettyServerVisitor>> findPage(int size, int current, NettyServerVisitor nettyServerVisitor) {
        NettyServerVisitorDO nettyServerVisitorDO = NettyServerVisitorConverter.INSTANCE.fromNettyServerVisitor(nettyServerVisitor);
        LazyPage<NettyServerVisitor> lazyPage = new LazyPage<>(current, size);
        LazyPage<NettyServerVisitor> nettyServerVisitorLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(nettyServerVisitorDO), lazyPage, NettyServerVisitor.class);
        return ResultFactory.successOf(nettyServerVisitorLazyPage);
    }

    /**
     * describe 删除服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 删除服务端提前开放出来的端口
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<NettyServerVisitor> remove(NettyServerVisitor nettyServerVisitor) {
        NettyServerVisitorDO nettyServerVisitorDO = NettyServerVisitorConverter.INSTANCE.fromNettyServerVisitor(nettyServerVisitor);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(nettyServerVisitorDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 服务端提前开放出来的端口领域对象
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    @Override
    public Result<Boolean> exists(NettyServerVisitor nettyServerVisitor) {
        NettyServerVisitorDO nettyServerVisitorDO = NettyServerVisitorConverter.INSTANCE.fromNettyServerVisitor(nettyServerVisitor);
        Boolean exists = lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(nettyServerVisitorDO));
        return ResultFactory.successOf(exists);
    }

}