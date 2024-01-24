package wu.framework.lazy.cloud.heartbeat.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.VisitorFlowApplication;
import org.springframework.web.bind.annotation.*;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowRemoveCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowUpdateCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryOneCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.assembler.VisitorFlowDTOAssembler;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
import java.util.stream.Collectors;
import jakarta.annotation.Resource;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlowRepository;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class VisitorFlowApplicationImpl implements VisitorFlowApplication {

    @Resource
    VisitorFlowRepository visitorFlowRepository;
    /**
     * describe 新增访客端流量
     *
     * @param visitorFlowStoryCommand 新增访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlow> story(VisitorFlowStoryCommand visitorFlowStoryCommand) {
        VisitorFlow visitorFlow = VisitorFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowStoryCommand);
        return visitorFlowRepository.story(visitorFlow);
    }
    /**
     * describe 批量新增访客端流量
     *
     * @param visitorFlowStoryCommandList 批量新增访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorFlow>> batchStory(List<VisitorFlowStoryCommand> visitorFlowStoryCommandList) {
        List<VisitorFlow> visitorFlowList = visitorFlowStoryCommandList.stream().map( VisitorFlowDTOAssembler.INSTANCE::toVisitorFlow).collect(Collectors.toList());
        return visitorFlowRepository.batchStory(visitorFlowList);
    }
    /**
     * describe 更新访客端流量
     *
     * @param visitorFlowUpdateCommand 更新访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlow> updateOne(VisitorFlowUpdateCommand visitorFlowUpdateCommand) {
        VisitorFlow visitorFlow = VisitorFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowUpdateCommand);
        return visitorFlowRepository.story(visitorFlow);
    }

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorFlowQueryOneCommand 查询单个访客端流量     
     * @return {@link Result<VisitorFlowDTO>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlowDTO> findOne(VisitorFlowQueryOneCommand visitorFlowQueryOneCommand) {
        VisitorFlow visitorFlow = VisitorFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowQueryOneCommand);
        return visitorFlowRepository.findOne(visitorFlow).convert(VisitorFlowDTOAssembler.INSTANCE::fromVisitorFlow);
    }

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 查询多个访客端流量     
     * @return {@link Result<List<VisitorFlowDTO>>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorFlowDTO>> findList(VisitorFlowQueryListCommand visitorFlowQueryListCommand) {
        VisitorFlow visitorFlow = VisitorFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowQueryListCommand);
        return visitorFlowRepository.findList(visitorFlow)        .convert(visitorFlows -> visitorFlows.stream().map(VisitorFlowDTOAssembler.INSTANCE::fromVisitorFlow).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 分页查询多个访客端流量     
     * @return {@link Result<LazyPage<VisitorFlowDTO>>} 分页访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<LazyPage<VisitorFlowDTO>> findPage(int size,int current,VisitorFlowQueryListCommand visitorFlowQueryListCommand) {
        VisitorFlow visitorFlow = VisitorFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowQueryListCommand);
        return visitorFlowRepository.findPage(size,current,visitorFlow)        .convert(page -> page.convert(VisitorFlowDTOAssembler.INSTANCE::fromVisitorFlow))            ;
    }

    /**
     * describe 删除访客端流量
     *
     * @param visitorFlowRemoveCommand 删除访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorFlow> remove(VisitorFlowRemoveCommand visitorFlowRemoveCommand) {
     VisitorFlow visitorFlow = VisitorFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowRemoveCommand);
     return visitorFlowRepository.remove(visitorFlow);
    }

}