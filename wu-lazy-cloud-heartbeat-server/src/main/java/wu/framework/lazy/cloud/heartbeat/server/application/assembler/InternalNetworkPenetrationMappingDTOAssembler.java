package wu.framework.lazy.cloud.heartbeat.server.application.assembler;

import wu.framework.lazy.cloud.heartbeat.server.application.command.internal.network.penetration.mapping.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.InternalNetworkPenetrationMappingDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.internal.network.penetration.mapping.InternalNetworkPenetrationMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface InternalNetworkPenetrationMappingDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
    InternalNetworkPenetrationMappingDTOAssembler INSTANCE = Mappers.getMapper(InternalNetworkPenetrationMappingDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param internalNetworkPenetrationMappingStoryCommand 保存内网穿透映射对象     
     * @return {@link InternalNetworkPenetrationMapping} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMapping toInternalNetworkPenetrationMapping(InternalNetworkPenetrationMappingStoryCommand internalNetworkPenetrationMappingStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param internalNetworkPenetrationMappingUpdateCommand 更新内网穿透映射对象     
     * @return {@link InternalNetworkPenetrationMapping} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMapping toInternalNetworkPenetrationMapping(InternalNetworkPenetrationMappingUpdateCommand internalNetworkPenetrationMappingUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param internalNetworkPenetrationMappingQueryOneCommand 查询单个内网穿透映射对象参数     
     * @return {@link InternalNetworkPenetrationMapping} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMapping toInternalNetworkPenetrationMapping(InternalNetworkPenetrationMappingQueryOneCommand internalNetworkPenetrationMappingQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 查询集合内网穿透映射对象参数     
     * @return {@link InternalNetworkPenetrationMapping} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMapping toInternalNetworkPenetrationMapping(InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param internalNetworkPenetrationMappingRemoveCommand 删除内网穿透映射对象参数     
     * @return {@link InternalNetworkPenetrationMapping} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMapping toInternalNetworkPenetrationMapping(InternalNetworkPenetrationMappingRemoveCommand internalNetworkPenetrationMappingRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param internalNetworkPenetrationMapping 内网穿透映射领域对象     
     * @return {@link InternalNetworkPenetrationMappingDTO} 内网穿透映射DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMappingDTO fromInternalNetworkPenetrationMapping(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping);
}