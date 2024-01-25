package wu.framework.lazy.cloud.heartbeat.server.infrastructure.converter;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import wu.framework.lazy.cloud.heartbeat.server.infrastructure.entity.NettyClientBlacklistDO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist.NettyClientBlacklist;

/**
 * describe 客户端黑名单
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyInfrastructureConverter
 **/
@Mapper
public interface NettyClientBlacklistConverter {


    /**
     * describe MapStruct 创建的代理对象
     *
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklistConverter INSTANCE = Mappers.getMapper(NettyClientBlacklistConverter.class);

    /**
     * describe 实体对象 转换成领域对象
     *
     * @param nettyClientBlacklistDO 客户端黑名单实体对象
     * @return {@link NettyClientBlacklist} 客户端黑名单领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklist toNettyClientBlacklist(NettyClientBlacklistDO nettyClientBlacklistDO);

    /**
     * describe 领域对象 转换成实体对象
     *
     * @param nettyClientBlacklist 客户端黑名单领域对象
     * @return {@link NettyClientBlacklistDO} 客户端黑名单实体对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    NettyClientBlacklistDO fromNettyClientBlacklist(NettyClientBlacklist nettyClientBlacklist);
}