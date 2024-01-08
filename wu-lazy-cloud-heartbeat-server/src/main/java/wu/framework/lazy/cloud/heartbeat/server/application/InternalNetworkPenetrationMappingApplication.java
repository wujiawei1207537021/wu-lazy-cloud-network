package wu.framework.lazy.cloud.heartbeat.server.application;


import wu.framework.lazy.cloud.heartbeat.server.application.command.internal.network.penetration.mapping.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.InternalNetworkPenetrationMappingDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.internal.network.penetration.mapping.InternalNetworkPenetrationMapping;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;

import java.util.List;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface InternalNetworkPenetrationMappingApplication {


    /**
     * describe 新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingStoryCommand 新增内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMapping> story(InternalNetworkPenetrationMappingStoryCommand internalNetworkPenetrationMappingStoryCommand);

    /**
     * describe 批量新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingStoryCommandList 批量新增内网穿透映射     
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<List<InternalNetworkPenetrationMapping>> batchStory(List<InternalNetworkPenetrationMappingStoryCommand> internalNetworkPenetrationMappingStoryCommandList);

    /**
     * describe 更新内网穿透映射
     *
     * @param internalNetworkPenetrationMappingUpdateCommand 更新内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMapping> updateOne(InternalNetworkPenetrationMappingUpdateCommand internalNetworkPenetrationMappingUpdateCommand);

    /**
     * describe 查询单个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryOneCommand 查询单个内网穿透映射     
     * @return {@link Result< InternalNetworkPenetrationMappingDTO >} 内网穿透映射DTO对象
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMappingDTO> findOne(InternalNetworkPenetrationMappingQueryOneCommand internalNetworkPenetrationMappingQueryOneCommand);

    /**
     * describe 查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 查询多个内网穿透映射     
     * @return {@link Result <List<InternalNetworkPenetrationMappingDTO>>} 内网穿透映射DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result <List<InternalNetworkPenetrationMappingDTO>> findList(InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand);

    /**
     * describe 分页查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 分页查询多个内网穿透映射     
     * @return {@link Result <LazyPage<InternalNetworkPenetrationMappingDTO>>} 分页内网穿透映射DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result <LazyPage<InternalNetworkPenetrationMappingDTO>> findPage(int size,int current,InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand);

    /**
     * describe 删除内网穿透映射
     *
     * @param internalNetworkPenetrationMappingRemoveCommand 删除内网穿透映射     
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    Result<InternalNetworkPenetrationMapping> remove(InternalNetworkPenetrationMappingRemoveCommand internalNetworkPenetrationMappingRemoveCommand);

    /**
     * 创建客户端的访问者
     * @param clientId 客户端ID
     */
    Result<Void> createVisitor(String clientId);
}