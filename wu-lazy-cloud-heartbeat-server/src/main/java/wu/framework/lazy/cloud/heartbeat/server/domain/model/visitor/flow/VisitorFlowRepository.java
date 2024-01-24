package wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 访客端流量 
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface VisitorFlowRepository {


    /**
     * describe 新增访客端流量
     *
     * @param visitorFlow 新增访客端流量     
     * @return {@link  Result<VisitorFlow>} 访客端流量新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlow> story(VisitorFlow visitorFlow);

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorFlowList 批量新增访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<List<VisitorFlow>> batchStory(List<VisitorFlow> visitorFlowList);

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorFlow 查询单个访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlow> findOne(VisitorFlow visitorFlow);

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorFlow 查询多个访客端流量     
     * @return {@link Result<List<VisitorFlow>>} 访客端流量DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<List<VisitorFlow>> findList(VisitorFlow visitorFlow);

    /**
     * describe 分页查询多个访客端流量
     *
     * @param size 当前页数
     * @param current 当前页
     * @param visitorFlow 分页查询多个访客端流量     
     * @return {@link Result<LazyPage<VisitorFlow>>} 分页访客端流量领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<LazyPage<VisitorFlow>> findPage(int size,int current,VisitorFlow visitorFlow);

    /**
     * describe 删除访客端流量
     *
     * @param visitorFlow 删除访客端流量     
     * @return {@link Result<VisitorFlow>} 访客端流量     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<VisitorFlow> remove(VisitorFlow visitorFlow);

    /**
     * describe 是否存在访客端流量
     *
     * @param visitorFlow 是否存在访客端流量     
     * @return {@link Result<Boolean>} 访客端流量是否存在     
     
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    Result<Boolean> exists(VisitorFlow visitorFlow);

}