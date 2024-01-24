package wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter;

import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.VisitorFlowDO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter 
 **/
@Mapper
public interface VisitorFlowConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorFlowConverter INSTANCE = Mappers.getMapper(VisitorFlowConverter.class);
    /**
     * describe 实体对象 转换成领域对象
     *
     * @param visitorFlowDO 访客端流量实体对象     
     * @return {@link VisitorFlow} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorFlow toVisitorFlow(VisitorFlowDO visitorFlowDO);
    /**
     * describe 领域对象 转换成实体对象
     *
     * @param visitorFlow 访客端流量领域对象     
     * @return {@link VisitorFlowDO} 访客端流量实体对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlowDO fromVisitorFlow(VisitorFlow visitorFlow); 
}