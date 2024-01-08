package wu.framework.lazy.cloud.heartbeat.server.application.impl;

import wu.framework.lazy.cloud.heartbeat.common.InternalNetworkPenetrationRealClient;
import wu.framework.lazy.cloud.heartbeat.server.application.InternalNetworkPenetrationMappingApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.assembler.InternalNetworkPenetrationMappingDTOAssembler;
import wu.framework.lazy.cloud.heartbeat.server.application.command.internal.network.penetration.mapping.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.InternalNetworkPenetrationMappingDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.internal.network.penetration.mapping.InternalNetworkPenetrationMapping;
import wu.framework.lazy.cloud.heartbeat.server.model.internal.network.penetration.mapping.InternalNetworkPenetrationMappingRepository;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import jakarta.annotation.Resource;
import wu.framework.lazy.cloud.heartbeat.server.netty.filter.VisitorFilter;
import wu.framework.lazy.cloud.heartbeat.server.netty.socket.NettyVisitorSocket;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 内网穿透映射
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class InternalNetworkPenetrationMappingApplicationImpl implements InternalNetworkPenetrationMappingApplication {

    @Resource
    InternalNetworkPenetrationMappingRepository internalNetworkPenetrationMappingRepository;


    /**
     * describe 新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingStoryCommand 新增内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMapping> story(InternalNetworkPenetrationMappingStoryCommand internalNetworkPenetrationMappingStoryCommand) {
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = InternalNetworkPenetrationMappingDTOAssembler.INSTANCE.toInternalNetworkPenetrationMapping(internalNetworkPenetrationMappingStoryCommand);
        return internalNetworkPenetrationMappingRepository.story(internalNetworkPenetrationMapping);
    }

    /**
     * describe 批量新增内网穿透映射
     *
     * @param internalNetworkPenetrationMappingStoryCommandList 批量新增内网穿透映射
     * @return {@link Result<List<InternalNetworkPenetrationMapping>>} 内网穿透映射新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<List<InternalNetworkPenetrationMapping>> batchStory(List<InternalNetworkPenetrationMappingStoryCommand> internalNetworkPenetrationMappingStoryCommandList) {
        List<InternalNetworkPenetrationMapping> internalNetworkPenetrationMappingList = internalNetworkPenetrationMappingStoryCommandList.stream().map(InternalNetworkPenetrationMappingDTOAssembler.INSTANCE::toInternalNetworkPenetrationMapping).collect(Collectors.toList());
        return internalNetworkPenetrationMappingRepository.batchStory(internalNetworkPenetrationMappingList);
    }

    /**
     * describe 更新内网穿透映射
     *
     * @param internalNetworkPenetrationMappingUpdateCommand 更新内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射领域对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMapping> updateOne(InternalNetworkPenetrationMappingUpdateCommand internalNetworkPenetrationMappingUpdateCommand) {
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = InternalNetworkPenetrationMappingDTOAssembler.INSTANCE.toInternalNetworkPenetrationMapping(internalNetworkPenetrationMappingUpdateCommand);
        return internalNetworkPenetrationMappingRepository.story(internalNetworkPenetrationMapping);
    }

    /**
     * describe 查询单个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryOneCommand 查询单个内网穿透映射
     * @return {@link Result< InternalNetworkPenetrationMappingDTO >} 内网穿透映射DTO对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMappingDTO> findOne(InternalNetworkPenetrationMappingQueryOneCommand internalNetworkPenetrationMappingQueryOneCommand) {
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = InternalNetworkPenetrationMappingDTOAssembler.INSTANCE.toInternalNetworkPenetrationMapping(internalNetworkPenetrationMappingQueryOneCommand);
        return internalNetworkPenetrationMappingRepository.findOne(internalNetworkPenetrationMapping).convert(InternalNetworkPenetrationMappingDTOAssembler.INSTANCE::fromInternalNetworkPenetrationMapping);
    }

    /**
     * describe 查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 查询多个内网穿透映射
     * @return {@link Result<List<InternalNetworkPenetrationMappingDTO>>} 内网穿透映射DTO对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<List<InternalNetworkPenetrationMappingDTO>> findList(InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand) {
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = InternalNetworkPenetrationMappingDTOAssembler.INSTANCE.toInternalNetworkPenetrationMapping(internalNetworkPenetrationMappingQueryListCommand);
        return internalNetworkPenetrationMappingRepository.findList(internalNetworkPenetrationMapping).convert(internalNetworkPenetrationMappings -> internalNetworkPenetrationMappings.stream().map(InternalNetworkPenetrationMappingDTOAssembler.INSTANCE::fromInternalNetworkPenetrationMapping).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个内网穿透映射
     *
     * @param internalNetworkPenetrationMappingQueryListCommand 分页查询多个内网穿透映射
     * @return {@link Result<LazyPage<InternalNetworkPenetrationMappingDTO>>} 分页内网穿透映射DTO对象
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<LazyPage<InternalNetworkPenetrationMappingDTO>> findPage(int size, int current, InternalNetworkPenetrationMappingQueryListCommand internalNetworkPenetrationMappingQueryListCommand) {
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = InternalNetworkPenetrationMappingDTOAssembler.INSTANCE.toInternalNetworkPenetrationMapping(internalNetworkPenetrationMappingQueryListCommand);
        return internalNetworkPenetrationMappingRepository.findPage(size, current, internalNetworkPenetrationMapping).convert(page -> page.convert(InternalNetworkPenetrationMappingDTOAssembler.INSTANCE::fromInternalNetworkPenetrationMapping));
    }

    /**
     * describe 删除内网穿透映射
     *
     * @param internalNetworkPenetrationMappingRemoveCommand 删除内网穿透映射
     * @return {@link Result<InternalNetworkPenetrationMapping>} 内网穿透映射
     * @author Jia wei Wu
     * @date 2023/12/29 05:21 下午
     **/

    @Override
    public Result<InternalNetworkPenetrationMapping> remove(InternalNetworkPenetrationMappingRemoveCommand internalNetworkPenetrationMappingRemoveCommand) {
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = InternalNetworkPenetrationMappingDTOAssembler.INSTANCE.toInternalNetworkPenetrationMapping(internalNetworkPenetrationMappingRemoveCommand);
        return internalNetworkPenetrationMappingRepository.remove(internalNetworkPenetrationMapping);
    }

    /**
     * 创建客户端的访问者
     *
     * @param clientId 客户端ID
     */
    @Override
    public Result<Void> createVisitor(String clientId) {
        // 查询代理信息
        InternalNetworkPenetrationMapping internalNetworkPenetrationMapping = new InternalNetworkPenetrationMapping();
        internalNetworkPenetrationMapping.setIsDeleted(false);
        internalNetworkPenetrationMapping.setClientId(clientId);
        return internalNetworkPenetrationMappingRepository
                .findList(internalNetworkPenetrationMapping)
                .applyOther(internalNetworkPenetrationMappings -> {
                    for (InternalNetworkPenetrationMapping networkPenetrationMapping : internalNetworkPenetrationMappings) {
                        Integer visitorPort = networkPenetrationMapping.getVisitorPort();
                        String clientTargetIp = networkPenetrationMapping.getClientTargetIp();
                        Integer clientTargetPort = networkPenetrationMapping.getClientTargetPort();

                        InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient = new InternalNetworkPenetrationRealClient();
                        internalNetworkPenetrationRealClient.setClientTargetIp(clientTargetIp);
                        internalNetworkPenetrationRealClient.setClientTargetPort(clientTargetPort);
                        internalNetworkPenetrationRealClient.setClientId(clientId);
                        internalNetworkPenetrationRealClient.setVisitorPort(visitorPort);

                        // 创建服务端代理连接
                        VisitorFilter visitorFilter = new VisitorFilter(internalNetworkPenetrationRealClient);
                        NettyVisitorSocket nettyVisitorSocket = new NettyVisitorSocket(visitorFilter);

                            Thread thread = new Thread(() -> {
                                try {
                                    nettyVisitorSocket.startServer(visitorPort);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }

                            });
                            // 使用线程池 TODO
                            thread.run();



                        // 发送客户端代理连接请求  客户端创建代理连接
//                        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId.getBytes(StandardCharsets.UTF_8));
//                        if (!ObjectUtils.isEmpty(clientChannel)) {
//                            Channel channel = clientChannel.getChannel();
//                            NettyProxyMsg nettyMsg = new NettyProxyMsg();
//                            nettyMsg.setType(MessageType.DISTRIBUTE_SINGLE_CLIENT_REAL_CONNECT);
//                            nettyMsg.setClientId(clientId.getBytes(StandardCharsets.UTF_8));
//                            nettyMsg.setData(JSON.toJSONString(internalNetworkPenetrationRealClient).getBytes(StandardCharsets.UTF_8));
//                            channel.writeAndFlush(nettyMsg);
//                        }


                    }


                    return ResultFactory.successOf();
                });

    }
}