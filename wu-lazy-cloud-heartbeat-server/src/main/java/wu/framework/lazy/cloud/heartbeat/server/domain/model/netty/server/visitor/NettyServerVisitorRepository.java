package wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor;

import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import org.springframework.beans.factory.annotation.Autowired;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;
import java.util.List;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
/**
 * describe 服务端提前开放出来的端口 
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyDomainRepository 
 **/

public interface NettyServerVisitorRepository {


    /**
     * describe 新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 新增服务端提前开放出来的端口     
     * @return {@link  Result<NettyServerVisitor>} 服务端提前开放出来的端口新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitor> story(NettyServerVisitor nettyServerVisitor);

    /**
     * describe 批量新增服务端提前开放出来的端口
     *
     * @param nettyServerVisitorList 批量新增服务端提前开放出来的端口     
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<List<NettyServerVisitor>> batchStory(List<NettyServerVisitor> nettyServerVisitorList);

    /**
     * describe 查询单个服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 查询单个服务端提前开放出来的端口     
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitor> findOne(NettyServerVisitor nettyServerVisitor);

    /**
     * describe 查询多个服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 查询多个服务端提前开放出来的端口     
     * @return {@link Result<List<NettyServerVisitor>>} 服务端提前开放出来的端口DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<List<NettyServerVisitor>> findList(NettyServerVisitor nettyServerVisitor);

    /**
     * describe 分页查询多个服务端提前开放出来的端口
     *
     * @param size 当前页数
     * @param current 当前页
     * @param nettyServerVisitor 分页查询多个服务端提前开放出来的端口     
     * @return {@link Result<LazyPage<NettyServerVisitor>>} 分页服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<LazyPage<NettyServerVisitor>> findPage(int size,int current,NettyServerVisitor nettyServerVisitor);

    /**
     * describe 删除服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 删除服务端提前开放出来的端口     
     * @return {@link Result<NettyServerVisitor>} 服务端提前开放出来的端口     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<NettyServerVisitor> remove(NettyServerVisitor nettyServerVisitor);

    /**
     * describe 是否存在服务端提前开放出来的端口
     *
     * @param nettyServerVisitor 是否存在服务端提前开放出来的端口     
     * @return {@link Result<Boolean>} 服务端提前开放出来的端口是否存在     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/

    Result<Boolean> exists(NettyServerVisitor nettyServerVisitor);

}