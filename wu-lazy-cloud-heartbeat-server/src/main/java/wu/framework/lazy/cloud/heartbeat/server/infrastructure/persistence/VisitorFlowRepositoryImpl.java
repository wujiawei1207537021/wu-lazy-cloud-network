package wu.framework.lazy.cloud.heartbeat.server.infrastructure.persistence;

import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.VisitorFlowDO;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter.VisitorFlowConverter;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.mapper.VisitorFlowMapper;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlowRepository;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class VisitorFlowRepositoryImpl  implements  VisitorFlowRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增访客端流量
     *
     * @param visitorFlow 新增访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlow> story(VisitorFlow visitorFlow) {
        VisitorFlowDO visitorFlowDO = VisitorFlowConverter.INSTANCE.fromVisitorFlow(visitorFlow);
        lazyLambdaStream.upsertRemoveNull(visitorFlowDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorFlowList 批量新增访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorFlow>> batchStory(List<VisitorFlow> visitorFlowList) {
        List<VisitorFlowDO> visitorFlowDOList = visitorFlowList.stream().map(VisitorFlowConverter.INSTANCE::fromVisitorFlow).collect(Collectors.toList());
        lazyLambdaStream.upsert(visitorFlowDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorFlow 查询单个访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlow> findOne(VisitorFlow visitorFlow) {
        VisitorFlowDO visitorFlowDO = VisitorFlowConverter.INSTANCE.fromVisitorFlow(visitorFlow);
        VisitorFlow visitorFlowOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(visitorFlowDO), VisitorFlow.class);
        return ResultFactory.successOf(visitorFlowOne);
    }

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorFlow 查询多个访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorFlow>> findList(VisitorFlow visitorFlow) {
        VisitorFlowDO visitorFlowDO = VisitorFlowConverter.INSTANCE.fromVisitorFlow(visitorFlow);
        List<VisitorFlow> visitorFlowList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(visitorFlowDO), VisitorFlow.class);
        return ResultFactory.successOf(visitorFlowList);
    }

    /**
     * describe 分页查询多个访客端流量
     *
     * @param size 当前页数
     * @param current 当前页
     * @param visitorFlow 分页查询多个访客端流量     
     * @return {@link Result<LazyPage<VisitorFlow>>} 分页访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<LazyPage<VisitorFlow>> findPage(int size,int current,VisitorFlow visitorFlow) {
        VisitorFlowDO visitorFlowDO = VisitorFlowConverter.INSTANCE.fromVisitorFlow(visitorFlow);
        LazyPage<VisitorFlow> lazyPage = new LazyPage<>(current,size);
        LazyPage<VisitorFlow> visitorFlowLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(visitorFlowDO),lazyPage, VisitorFlow.class);
        return ResultFactory.successOf(visitorFlowLazyPage);
    }

    /**
     * describe 删除访客端流量
     *
     * @param visitorFlow 删除访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlow> remove(VisitorFlow visitorFlow) {
        VisitorFlowDO visitorFlowDO = VisitorFlowConverter.INSTANCE.fromVisitorFlow(visitorFlow);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(visitorFlowDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在访客端流量
     *
     * @param visitorFlow 访客端流量领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<Boolean> exists(VisitorFlow visitorFlow) {
        VisitorFlowDO visitorFlowDO = VisitorFlowConverter.INSTANCE.fromVisitorFlow(visitorFlow);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(visitorFlowDO));
        return ResultFactory.successOf(exists);
    }

}