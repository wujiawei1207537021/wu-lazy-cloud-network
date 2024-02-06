package wu.framework.lazy.cloud.heartbeat.server.infrastructure.persistence;


import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlowRepository;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter.VisitorPortFlowConverter;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.VisitorPortFlowDO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 访客端流量
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence
 **/
@Repository
public class VisitorPortFlowRepositoryImpl implements VisitorPortFlowRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增访客端流量
     *
     * @param visitorPortFlow 新增访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlow> story(VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        lazyLambdaStream.upsertRemoveNull(visitorPortFlowDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorPortFlowList 批量新增访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorPortFlow>> batchStory(List<VisitorPortFlow> visitorPortFlowList) {
        List<VisitorPortFlowDO> visitorPortFlowDOList = visitorPortFlowList.stream().map(VisitorPortFlowConverter.INSTANCE::fromVisitorFlow).collect(Collectors.toList());
        lazyLambdaStream.upsert(visitorPortFlowDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorPortFlow 查询单个访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlow> findOne(VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        VisitorPortFlow visitorPortFlowOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(visitorPortFlowDO), VisitorPortFlow.class);
        return ResultFactory.successOf(visitorPortFlowOne);
    }

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorPortFlow 查询多个访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorPortFlow>> findList(VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        List<VisitorPortFlow> visitorPortFlowList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(visitorPortFlowDO), VisitorPortFlow.class);
        return ResultFactory.successOf(visitorPortFlowList);
    }

    /**
     * describe 分页查询多个访客端流量
     *
     * @param size            当前页数
     * @param current         当前页
     * @param visitorPortFlow 分页查询多个访客端流量
     * @return {@link Result<LazyPage< VisitorPortFlow >>} 分页访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<LazyPage<VisitorPortFlow>> findPage(int size, int current, VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        LazyPage<VisitorPortFlow> lazyPage = new LazyPage<>(current, size);
        LazyPage<VisitorPortFlow> visitorFlowLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(visitorPortFlowDO), lazyPage, VisitorPortFlow.class);
        return ResultFactory.successOf(visitorFlowLazyPage);
    }

    /**
     * describe 删除访客端流量
     *
     * @param visitorPortFlow 删除访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlow> remove(VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(visitorPortFlowDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在访客端流量
     *
     * @param visitorPortFlow 访客端流量领域对象
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<Boolean> exists(VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        Boolean exists = lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(visitorPortFlowDO));
        return ResultFactory.successOf(exists);
    }

    /**
     * 根据客户端查询流量
     *
     * @param size            分页大小
     * @param current         分页
     * @param visitorPortFlow 查询条件
     * @return {@link Result<LazyPage<  VisitorPortFlow  >>} 分页访客端流量DTO对象
     */
    @Override
    public Result<LazyPage<VisitorPortFlow>> findPageGroupByClientId(int size, int current, VisitorPortFlow visitorPortFlow) {
        VisitorPortFlowDO visitorPortFlowDO = VisitorPortFlowConverter.INSTANCE.fromVisitorFlow(visitorPortFlow);
        LazyPage<VisitorPortFlow> lazyPage = new LazyPage<>(current, size);
        LazyPage<VisitorPortFlow> visitorFlowLazyPage = lazyLambdaStream
                .selectPage(
                        LazyWrappers.lambdaWrapperBean(visitorPortFlowDO)
                                .groupBy(VisitorPortFlowDO::getClientId),
                        lazyPage,
                        VisitorPortFlow.class);
        return ResultFactory.successOf(visitorFlowLazyPage);
    }

    /**
     * 根据客户端ID查询出 客户端所有的进出口流量
     *
     * @param clientIdList 客户端ID
     * @return 客户端所有的进出口流量
     */
    @Override
    public List<VisitorPortFlow> findListByClientIds(List<String> clientIdList) {
        if (ObjectUtils.isEmpty(clientIdList)) {
            return new ArrayList<>();
        }
        return lazyLambdaStream.selectList(LazyWrappers.<VisitorPortFlowDO>lambdaWrapper()
                .in(VisitorPortFlowDO::getClientId, clientIdList), VisitorPortFlow.class);
    }
}