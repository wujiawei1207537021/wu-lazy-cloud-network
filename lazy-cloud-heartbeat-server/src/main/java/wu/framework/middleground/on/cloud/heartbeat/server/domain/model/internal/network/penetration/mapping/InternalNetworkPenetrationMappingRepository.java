package wu.framework.middleground.on.cloud.heartbeat.server.domain.model.internal.network.penetration.mapping;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface InternalNetworkPenetrationMappingRepository {


    /**
     * describe 新增内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 新增内网穿透映射     
     * @return {@link  Result<InternalNetworkPenetrationMapping>} 内网穿透映射新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMapping> story(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);

    /**
     * describe 批量新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingList 批量新增内网穿透映射     
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<List<InternalNetworkPenetrationMapping>> batchStory(List<InternalNetworkPenetrationMapping> internalNetworkPenetrationMappingList);

    /**
     * describe 查询单个内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 查询单个内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMapping> findOne(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);

    /**
     * describe 查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 查询多个内网穿透映射     
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<List<InternalNetworkPenetrationMapping>> findList(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);

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

    Result<LazyPage<InternalNetworkPenetrationMapping>> findPage(int size,int current,InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);

    /**
     * describe 删除内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 删除内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMapping> remove(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);

    /**
     * describe 是否存在内网穿透映射
     *
     * @param internalNetworkPenetrationMapping 是否存在内网穿透映射     
     * @return {@link Result<Boolean>} 内网穿透映射是否存在     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<Boolean> exists(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);

}