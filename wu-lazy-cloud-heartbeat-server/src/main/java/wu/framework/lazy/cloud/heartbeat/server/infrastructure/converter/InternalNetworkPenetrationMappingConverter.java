package wu.framework.lazy.on.cloud.heartbeat.server.domain.infrastructure.converter;

import wu.framework.lazy.on.cloud.heartbeat.server.domain.model.internal.network.penetration.mapping.InternalNetworkPenetrationMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.infrastructure.entity.InternalNetworkPenetrationMappingDO;
/**
 * describe 内网穿透映射 
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface InternalNetworkPenetrationMappingConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
    InternalNetworkPenetrationMappingConverter INSTANCE = Mappers.getMapper(InternalNetworkPenetrationMappingConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param internalNetworkPenetrationMappingDO 内网穿透映射实体对象     
     * @return {@link InternalNetworkPenetrationMapping} 内网穿透映射领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
    InternalNetworkPenetrationMapping toInternalNetworkPenetrationMapping(InternalNetworkPenetrationMappingDO internalNetworkPenetrationMappingDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param internalNetworkPenetrationMapping 内网穿透映射领域对象     
     * @return {@link InternalNetworkPenetrationMappingDO} 内网穿透映射实体对象     
     
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/
     InternalNetworkPenetrationMappingDO fromInternalNetworkPenetrationMapping(InternalNetworkPenetrationMapping internalNetworkPenetrationMapping); 
}