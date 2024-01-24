package wu.framework.lazy.cloud.heartbeat.server.application;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowRemoveCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowUpdateCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorFlowQueryOneCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface VisitorFlowApplication {


    /**
     * describe 新增访客端流量
     *
     * @param visitorFlowStoryCommand 新增访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlow> story(VisitorFlowStoryCommand visitorFlowStoryCommand);

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorFlowStoryCommandList 批量新增访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<List<VisitorFlow>> batchStory(List<VisitorFlowStoryCommand> visitorFlowStoryCommandList);

    /**
     * describe 更新访客端流量
     *
     * @param visitorFlowUpdateCommand 更新访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlow> updateOne(VisitorFlowUpdateCommand visitorFlowUpdateCommand);

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorFlowQueryOneCommand 查询单个访客端流量     
     * @return {@link Result<VisitorFlowDTO>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlowDTO> findOne(VisitorFlowQueryOneCommand visitorFlowQueryOneCommand);

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 查询多个访客端流量     
     * @return {@link Result <List<VisitorFlowDTO>>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result <List<VisitorFlowDTO>> findList(VisitorFlowQueryListCommand visitorFlowQueryListCommand);

    /**
     * describe 分页查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 分页查询多个访客端流量     
     * @return {@link Result <LazyPage<VisitorFlowDTO>>} 分页访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result <LazyPage<VisitorFlowDTO>> findPage(int size,int current,VisitorFlowQueryListCommand visitorFlowQueryListCommand);

    /**
     * describe 删除访客端流量
     *
     * @param visitorFlowRemoveCommand 删除访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlow> remove(VisitorFlowRemoveCommand visitorFlowRemoveCommand);

}