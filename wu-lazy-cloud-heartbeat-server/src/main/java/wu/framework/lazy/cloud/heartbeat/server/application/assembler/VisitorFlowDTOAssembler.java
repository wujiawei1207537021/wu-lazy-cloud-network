package wu.framework.lazy.cloud.heartbeat.server.application.assembler;

import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowRemoveCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowUpdateCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryOneCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
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
public interface VisitorFlowDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
    VisitorFlowDTOAssembler INSTANCE = Mappers.getMapper(VisitorFlowDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param visitorFlowStoryCommand 保存访客端流量对象     
     * @return {@link VisitorFlow} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlow toVisitorFlow(VisitorFlowStoryCommand visitorFlowStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param visitorFlowUpdateCommand 更新访客端流量对象     
     * @return {@link VisitorFlow} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlow toVisitorFlow(VisitorFlowUpdateCommand visitorFlowUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param visitorFlowQueryOneCommand 查询单个访客端流量对象参数     
     * @return {@link VisitorFlow} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlow toVisitorFlow(VisitorFlowQueryOneCommand visitorFlowQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param visitorFlowQueryListCommand 查询集合访客端流量对象参数     
     * @return {@link VisitorFlow} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlow toVisitorFlow(VisitorFlowQueryListCommand visitorFlowQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param visitorFlowRemoveCommand 删除访客端流量对象参数     
     * @return {@link VisitorFlow} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlow toVisitorFlow(VisitorFlowRemoveCommand visitorFlowRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param visitorFlow 访客端流量领域对象     
     * @return {@link VisitorFlowDTO} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/
     VisitorFlowDTO fromVisitorFlow(VisitorFlow visitorFlow);
}