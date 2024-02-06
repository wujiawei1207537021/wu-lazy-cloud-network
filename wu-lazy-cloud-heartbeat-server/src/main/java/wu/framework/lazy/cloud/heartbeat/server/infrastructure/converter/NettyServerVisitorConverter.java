package wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.netty.server.visitor.NettyServerVisitor;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.NettyServerVisitorDO;

/**
 * describe 服务端提前开放出来的端口
 *
 * @author Jia wei Wu
 * @date 2024/01/16 02:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface NettyServerVisitorConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
    NettyServerVisitorConverter INSTANCE = Mappers.getMapper(NettyServerVisitorConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param nettyServerVisitorDO 服务端提前开放出来的端口实体对象
     * @return {@link NettyServerVisitor} 服务端提前开放出来的端口领域对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
    NettyServerVisitor toNettyServerVisitor(NettyServerVisitorDO nettyServerVisitorDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param nettyServerVisitor 服务端提前开放出来的端口领域对象
     * @return {@link NettyServerVisitorDO} 服务端提前开放出来的端口实体对象
     * @author Jia wei Wu
     * @date 2024/01/16 02:21 下午
     **/
    NettyServerVisitorDO fromNettyServerVisitor(NettyServerVisitor nettyServerVisitor);
}