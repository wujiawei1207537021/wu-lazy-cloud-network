package wu.framework.lazy.cloud.heartbeat.server.application.assembler;

import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.*;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorPortFlowQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorPortFlowDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;

/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface VisitorPortFlowDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorPortFlowDTOAssembler INSTANCE = Mappers.getMapper(VisitorPortFlowDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param visitorPortFlowStoryCommand 保存访客端流量对象
     * @return {@link VisitorPortFlow} 访客端流量领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorPortFlow toVisitorFlow(VisitorPortFlowStoryCommand visitorPortFlowStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param visitorPortFlowUpdateCommand 更新访客端流量对象
     * @return {@link VisitorPortFlow} 访客端流量领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorPortFlow toVisitorFlow(VisitorPortFlowUpdateCommand visitorPortFlowUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param visitorPortFlowQueryOneCommand 查询单个访客端流量对象参数
     * @return {@link VisitorPortFlow} 访客端流量领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorPortFlow toVisitorFlow(VisitorPortFlowQueryOneCommand visitorPortFlowQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param visitorPortFlowQueryListCommand 查询集合访客端流量对象参数
     * @return {@link VisitorPortFlow} 访客端流量领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorPortFlow toVisitorFlow(VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param visitorPortFlowRemoveCommand 删除访客端流量对象参数
     * @return {@link VisitorPortFlow} 访客端流量领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorPortFlow toVisitorFlow(VisitorPortFlowRemoveCommand visitorPortFlowRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param visitorPortFlow 访客端流量领域对象
     * @return {@link VisitorPortFlowDTO} 访客端流量DTO对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorPortFlowDTO fromVisitorFlow(VisitorPortFlow visitorPortFlow);
}