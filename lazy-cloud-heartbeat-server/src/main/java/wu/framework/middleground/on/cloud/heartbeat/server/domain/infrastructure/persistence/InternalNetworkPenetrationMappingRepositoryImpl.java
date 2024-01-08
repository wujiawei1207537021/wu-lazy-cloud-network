package wu.framework.middleground.on.cloud.heartbeat.server.domain.infrastructure.persistence;

import wu.framework.middleground.on.cloud.heartbeat.server.domain.infrastructure.converter.InternalNetworkPenetrationMappingConverter;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.infrastructure.entity.InternalNetworkPenetrationMappingDO;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.model.internal.network.penetration.mapping.InternalNetworkPenetrationMapping;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.model.internal.network.penetration.mapping.InternalNetworkPenetrationMappingRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.lambda.LazyLambdaStream;
import com.wu.framework.inner.lazy.database.expand.database.persistence.stream.wrapper.LazyWrappers;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructurePersistence 
 **/
@Repository
public class InternalNetworkPenetrationMappingRepositoryImpl  implements  InternalNetworkPenetrationMappingRepository {

    @Resource
    LazyLambdaStream lazyLambdaStream;

    /**
     * describe 新增内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 新增内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMapping> story(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping) {
        InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO = InternalNetworkPenetrationMappingConverter.INSTANCE.fromInternalNetworkPenetrationMapping(internalNetworkPenetrationMapping);
        lazyLambdaStream.upsert(internalNetworkPenetrationMappingDO);
        return ResultFactory.successOf();
    }

    /**
     * describe 批量新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingList 批量新增内网穿透映射     
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<List<InternalNetworkPenetrationMapping>> batchStory(List<InternalNetworkPenetrationMapping> internalNetworkPenetrationMappingList) {
        List<InternalNetworkPenetrationMappingDO> internalNetworkPenetrationMappingDOList = internalNetworkPenetrationMappingList.stream().map(InternalNetworkPenetrationMappingConverter.INSTANCE::fromInternalNetworkPenetrationMapping).collect(Collectors.toList());
        lazyLambdaStream.upsert(internalNetworkPenetrationMappingDOList);
        return ResultFactory.successOf();
    }

    /**
     * describe 查询单个内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 查询单个内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMapping> findOne(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping) {
        InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO = InternalNetworkPenetrationMappingConverter.INSTANCE.fromInternalNetworkPenetrationMapping(internalNetworkPenetrationMapping);
        InternalNetworkPenetrationMapping internalNetworkPenetrationMappingOne = lazyLambdaStream.selectOne(LazyWrappers.lambdaWrapperBean(internalNetworkPenetrationMappingDO), InternalNetworkPenetrationMapping.class);
        return ResultFactory.successOf(internalNetworkPenetrationMappingOne);
    }

    /**
     * describe 查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 查询多个内网穿透映射     
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<List<InternalNetworkPenetrationMapping>> findList(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping) {
        InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO = InternalNetworkPenetrationMappingConverter.INSTANCE.fromInternalNetworkPenetrationMapping(internalNetworkPenetrationMapping);
        List<InternalNetworkPenetrationMapping> internalNetworkPenetrationMappingList = lazyLambdaStream.selectList(LazyWrappers.lambdaWrapperBean(internalNetworkPenetrationMappingDO), InternalNetworkPenetrationMapping.class);
        return ResultFactory.successOf(internalNetworkPenetrationMappingList);
    }

    /**
     * describe 分页查询多个内网穿透映射
     *
     * @param size 当前页数
     * @param current 当前页
     * @param internalNetworkPenetrationMapping 分页查询多个内网穿透映射     
     * @return {@link Result<LazyPage<InternalNetworkPenetrationMapping>>} 分页内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<LazyPage<InternalNetworkPenetrationMapping>> findPage(int size,int current,InternalNetworkPenetrationMapping internalNetworkPenetrationMapping) {
        InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO = InternalNetworkPenetrationMappingConverter.INSTANCE.fromInternalNetworkPenetrationMapping(internalNetworkPenetrationMapping);
        LazyPage<InternalNetworkPenetrationMapping> lazyPage = new LazyPage<>(current,size);
        LazyPage<InternalNetworkPenetrationMapping> internalNetworkPenetrationMappingLazyPage = lazyLambdaStream.selectPage(LazyWrappers.lambdaWrapperBean(internalNetworkPenetrationMappingDO),lazyPage, InternalNetworkPenetrationMapping.class);
        return ResultFactory.successOf(internalNetworkPenetrationMappingLazyPage);
    }

    /**
     * describe 删除内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 删除内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMapping> remove(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping) {
        InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO = InternalNetworkPenetrationMappingConverter.INSTANCE.fromInternalNetworkPenetrationMapping(internalNetworkPenetrationMapping);
        //  lazyLambdaStream.delete(LazyWrappers.lambdaWrapperBean(internalNetworkPenetrationMappingDO));
        return ResultFactory.successOf();
    }

    /**
     * describe 是否存在内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 内网穿透映射领域对象     
     * @return {@link Result<Boolean>} 是否存在 true 存在，false 不存在     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<Boolean> exists(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping) {
        InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO = InternalNetworkPenetrationMappingConverter.INSTANCE.fromInternalNetworkPenetrationMapping(internalNetworkPenetrationMapping);
        Boolean exists=lazyLambdaStream.exists(LazyWrappers.lambdaWrapperBean(internalNetworkPenetrationMappingDO));
        return ResultFactory.successOf(exists);
    }

}