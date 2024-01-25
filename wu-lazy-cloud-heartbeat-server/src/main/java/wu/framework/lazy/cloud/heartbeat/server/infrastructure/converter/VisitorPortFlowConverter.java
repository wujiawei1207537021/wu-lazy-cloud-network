package wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.VisitorPortFlowDO;

/**
 * describe 访客端流量
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface VisitorPortFlowConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorPortFlowConverter INSTANCE = Mappers.getMapper(VisitorPortFlowConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param visitorPortFlowDO 访客端流量实体对象
     * @return {@link VisitorPortFlow} 访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorPortFlow toVisitorFlow(VisitorPortFlowDO visitorPortFlowDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param visitorPortFlow 访客端流量领域对象
     * @return {@link VisitorPortFlowDO} 访客端流量实体对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorPortFlowDO fromVisitorFlow(VisitorPortFlow visitorPortFlow);
}