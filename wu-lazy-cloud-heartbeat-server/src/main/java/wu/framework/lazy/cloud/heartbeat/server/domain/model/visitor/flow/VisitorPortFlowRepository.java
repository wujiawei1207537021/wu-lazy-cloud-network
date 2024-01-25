package wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow;

import ch.qos.logback.classic.util.LevelToSyslogSeverity;
import com.wu.framework.response.Result;

import java.util.List;

import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;

/**
 * describe 访客端流量
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository
 **/

public interface VisitorPortFlowRepository {


    /**
     * describe 新增访客端流量
     *
     * @param visitorPortFlow 新增访客端流量
     * @return {@link  Result< VisitorPortFlow >} 访客端流量新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlow> story(VisitorPortFlow visitorPortFlow);

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorPortFlowList 批量新增访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<List<VisitorPortFlow>> batchStory(List<VisitorPortFlow> visitorPortFlowList);

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorPortFlow 查询单个访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlow> findOne(VisitorPortFlow visitorPortFlow);

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorPortFlow 查询多个访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<List<VisitorPortFlow>> findList(VisitorPortFlow visitorPortFlow);

    /**
     * describe 分页查询多个访客端流量
     *
     * @param size            当前页数
     * @param current         当前页
     * @param visitorPortFlow 分页查询多个访客端流量
     * @return {@link Result<LazyPage< VisitorPortFlow >>} 分页访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<LazyPage<VisitorPortFlow>> findPage(int size, int current, VisitorPortFlow visitorPortFlow);

    /**
     * describe 删除访客端流量
     *
     * @param visitorPortFlow 删除访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorPortFlow> remove(VisitorPortFlow visitorPortFlow);

    /**
     * describe 是否存在访客端流量
     *
     * @param visitorPortFlow 是否存在访客端流量
     * @return {@link Result<Boolean>} 访客端流量是否存在
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<Boolean> exists(VisitorPortFlow visitorPortFlow);

    /**
     * 根据客户端查询流量
     *
     * @param size            分页大小
     * @param current         分页
     * @param visitorPortFlow 查询条件
     * @return {@link Result<LazyPage<  VisitorPortFlow  >>} 分页访客端流量DTO对象
     */
    Result<LazyPage<VisitorPortFlow>> findPageGroupByClientId(int size, int current, VisitorPortFlow visitorPortFlow);

    /**
     * 根据客户端ID查询出 客户端所有的进出口流量
     * @param clientIdList 客户端ID
     * @return 客户端所有的进出口流量
     */
    List<VisitorPortFlow> findListByClientIds(List<String> clientIdList);
}