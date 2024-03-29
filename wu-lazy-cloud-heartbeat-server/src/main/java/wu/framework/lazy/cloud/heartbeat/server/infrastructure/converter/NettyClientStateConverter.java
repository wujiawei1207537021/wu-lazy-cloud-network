package wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.NettyClientStateDO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;

/**
 * describe 客户端状态
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface NettyClientStateConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientStateConverter INSTANCE = Mappers.getMapper(NettyClientStateConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param nettyClientStateDO 客户端状态实体对象
     * @return {@link NettyClientState} 客户端状态领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientState toNettyClientState(NettyClientStateDO nettyClientStateDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param nettyClientState 客户端状态领域对象
     * @return {@link NettyClientStateDO} 客户端状态实体对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientStateDO fromNettyClientState(NettyClientState nettyClientState);
}