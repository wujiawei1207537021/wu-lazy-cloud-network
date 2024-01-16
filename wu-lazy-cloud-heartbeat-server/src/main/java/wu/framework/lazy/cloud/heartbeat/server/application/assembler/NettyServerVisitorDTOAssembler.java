package wu.framework.lazy.cloud.heartbeat.server.application.assembler;

import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorRemoveCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorStoryCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorUpdateCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorQueryListCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.server.visitor.NettyServerVisitorQueryOneCommand;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyServerVisitorDTO;
import org.mapstruct.factory.Mappers;
import org.mapstruct.Mapper;
/**
 * describe 服务端提前开放出来的端口 
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyAssembler 
 **/
@Mapper
public interface NettyServerVisitorDTOAssembler {


    /**
     * describe MapStruct 创建的代理对象
     *
     
     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
    NettyServerVisitorDTOAssembler INSTANCE = Mappers.getMapper(NettyServerVisitorDTOAssembler.class);
    /**
     * describe 应用层存储入参转换成 领域对象
     *
     * @param nettyServerVisitorStoryCommand 保存服务端提前开放出来的端口对象     
     * @return {@link NettyServerVisitor} 服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
     NettyServerVisitor toNettyServerVisitor(NettyServerVisitorStoryCommand nettyServerVisitorStoryCommand);
    /**
     * describe 应用层更新入参转换成 领域对象
     *
     * @param nettyServerVisitorUpdateCommand 更新服务端提前开放出来的端口对象     
     * @return {@link NettyServerVisitor} 服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
     NettyServerVisitor toNettyServerVisitor(NettyServerVisitorUpdateCommand nettyServerVisitorUpdateCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param nettyServerVisitorQueryOneCommand 查询单个服务端提前开放出来的端口对象参数     
     * @return {@link NettyServerVisitor} 服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
     NettyServerVisitor toNettyServerVisitor(NettyServerVisitorQueryOneCommand nettyServerVisitorQueryOneCommand);
    /**
     * describe 应用层查询入参转换成 领域对象
     *
     * @param nettyServerVisitorQueryListCommand 查询集合服务端提前开放出来的端口对象参数     
     * @return {@link NettyServerVisitor} 服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
     NettyServerVisitor toNettyServerVisitor(NettyServerVisitorQueryListCommand nettyServerVisitorQueryListCommand);
    /**
     * describe 应用层删除入参转换成 领域对象
     *
     * @param nettyServerVisitorRemoveCommand 删除服务端提前开放出来的端口对象参数     
     * @return {@link NettyServerVisitor} 服务端提前开放出来的端口领域对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
     NettyServerVisitor toNettyServerVisitor(NettyServerVisitorRemoveCommand nettyServerVisitorRemoveCommand);
    /**
     * describe 持久层领域对象转换成DTO对象
     *
     * @param nettyServerVisitor 服务端提前开放出来的端口领域对象     
     * @return {@link NettyServerVisitorDTO} 服务端提前开放出来的端口DTO对象     
     
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
     NettyServerVisitorDTO fromNettyServerVisitor(NettyServerVisitor nettyServerVisitor);
}