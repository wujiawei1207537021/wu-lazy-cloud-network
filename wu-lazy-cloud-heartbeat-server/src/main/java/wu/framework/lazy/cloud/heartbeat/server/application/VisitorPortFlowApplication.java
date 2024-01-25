package wu.framework.lazy.cloud.heartbeat.server.application;

import com.wu.framework.response.Result;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorPortFlowDTO;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.VisitorPortFlowStoryCommand;

import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;

/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplication 
 **/

public interface VisitorPortFlowApplication {


    /**
     * describe 新增访客端流量
     *
     * @param visitorPortFlowStoryCommand 新增访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量新增后领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlow> story(VisitorPortFlowStoryCommand visitorPortFlowStoryCommand);

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorPortFlowStoryCommandList 批量新增访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量新增后领域对象集合
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<List<VisitorPortFlow>> batchStory(List<VisitorPortFlowStoryCommand> visitorPortFlowStoryCommandList);

    /**
     * describe 更新访客端流量
     *
     * @param visitorPortFlowUpdateCommand 更新访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量领域对象
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlow> updateOne(VisitorPortFlowUpdateCommand visitorPortFlowUpdateCommand);

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorPortFlowQueryOneCommand 查询单个访客端流量
     * @return {@link Result< VisitorPortFlowDTO >} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlowDTO> findOne(VisitorPortFlowQueryOneCommand visitorPortFlowQueryOneCommand);

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorPortFlowQueryListCommand 查询多个访客端流量
     * @return {@link Result <List<VisitorPortFlowDTO>>} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result <List<VisitorPortFlowDTO>> findList(VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand);

    /**
     * describe 分页查询多个访客端流量
     *
     * @param visitorPortFlowQueryListCommand 分页查询多个访客端流量
     * @return {@link Result <LazyPage<VisitorPortFlowDTO>>} 分页访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result <LazyPage<VisitorPortFlowDTO>> findPage(int size, int current, VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand);

    /**
     * describe 删除访客端流量
     *
     * @param visitorPortFlowRemoveCommand 删除访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlow> remove(VisitorPortFlowRemoveCommand visitorPortFlowRemoveCommand);


    /**
     * 根据客户端查询流量
     * @param size 分页大小
     * @param current 分页
     * @param visitorPortFlowQueryListCommand 查询条件
     * @return {@link Result<LazyPage< VisitorFlowDTO >>} 分页访客端流量DTO对象
     */
    Result<LazyPage<VisitorFlowDTO>> findClientFlowPage(int size, int current, VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand);
}