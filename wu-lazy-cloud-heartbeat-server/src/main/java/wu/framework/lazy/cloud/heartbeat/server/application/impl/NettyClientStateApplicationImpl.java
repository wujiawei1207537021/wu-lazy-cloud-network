package wu.framework.lazy.cloud.heartbeat.server.application.impl;


import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import org.springframework.util.ObjectUtils;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyClientVisitorContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.server.application.NettyClientStateApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.assembler.NettyClientStateDTOAssembler;
import wu.framework.lazy.cloud.heartbeat.server.application.command.netty.client.state.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.NettyClientStateDTO;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientState;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.state.NettyClientStateRepository;
import wu.framework.lazy.cloud.heartbeat.server.netty.socket.NettyVisitorSocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * describe 客户端状态
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class NettyClientStateApplicationImpl implements NettyClientStateApplication {

    @Resource
    NettyClientStateRepository nettyClientStateRepository;

    /**
     * describe 新增客户端状态
     *
     * @param nettyClientStateStoryCommand 新增客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态新增后领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> story(NettyClientStateStoryCommand nettyClientStateStoryCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateStoryCommand);
        return nettyClientStateRepository.story(nettyClientState);
    }

    /**
     * describe 批量新增客户端状态
     *
     * @param nettyClientStateStoryCommandList 批量新增客户端状态
     * @return {@link Result<List<NettyClientState>>} 客户端状态新增后领域对象集合
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientState>> batchStory(List<NettyClientStateStoryCommand> nettyClientStateStoryCommandList) {
        List<NettyClientState> nettyClientStateList = nettyClientStateStoryCommandList.stream().map(NettyClientStateDTOAssembler.INSTANCE::toNettyClientState).collect(Collectors.toList());
        return nettyClientStateRepository.batchStory(nettyClientStateList);
    }

    /**
     * describe 更新客户端状态
     *
     * @param nettyClientStateUpdateCommand 更新客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态领域对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> updateOne(NettyClientStateUpdateCommand nettyClientStateUpdateCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateUpdateCommand);
        return nettyClientStateRepository.story(nettyClientState);
    }

    /**
     * describe 查询单个客户端状态
     *
     * @param nettyClientStateQueryOneCommand 查询单个客户端状态
     * @return {@link Result<NettyClientStateDTO>} 客户端状态DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientStateDTO> findOne(NettyClientStateQueryOneCommand nettyClientStateQueryOneCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateQueryOneCommand);
        return nettyClientStateRepository.findOne(nettyClientState).convert(NettyClientStateDTOAssembler.INSTANCE::fromNettyClientState);
    }

    /**
     * describe 查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 查询多个客户端状态
     * @return {@link Result<List<NettyClientStateDTO>>} 客户端状态DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientStateDTO>> findList(NettyClientStateQueryListCommand nettyClientStateQueryListCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateQueryListCommand);
        return nettyClientStateRepository.findList(nettyClientState).convert(nettyClientStates -> nettyClientStates.stream().map(NettyClientStateDTOAssembler.INSTANCE::fromNettyClientState).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个客户端状态
     *
     * @param nettyClientStateQueryListCommand 分页查询多个客户端状态
     * @return {@link Result<LazyPage<NettyClientStateDTO>>} 分页客户端状态DTO对象
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<LazyPage<NettyClientStateDTO>> findPage(int size, int current, NettyClientStateQueryListCommand nettyClientStateQueryListCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateQueryListCommand);
        return nettyClientStateRepository.findPage(size, current, nettyClientState).convert(page -> page.convert(NettyClientStateDTOAssembler.INSTANCE::fromNettyClientState));
    }

    /**
     * describe 删除客户端状态
     *
     * @param nettyClientStateRemoveCommand 删除客户端状态
     * @return {@link Result<NettyClientState>} 客户端状态
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientState> remove(NettyClientStateRemoveCommand nettyClientStateRemoveCommand) {
        NettyClientState nettyClientState = NettyClientStateDTOAssembler.INSTANCE.toNettyClientState(nettyClientStateRemoveCommand);
        // 获取当前客户端通道 而后关闭
        String clientId = nettyClientStateRemoveCommand.getClientId();
        // 心跳关闭
        ChannelContext.clear(clientId);
        // 关闭访客
        List<NettyVisitorSocket> nettyVisitorSocketList = NettyClientVisitorContext.getVisitorSockets(clientId);
        if(ObjectUtils.isEmpty(nettyVisitorSocketList)){
            for (NettyVisitorSocket nettyVisitorSocket : nettyVisitorSocketList) {
                try {
                    nettyVisitorSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return nettyClientStateRepository.remove(nettyClientState);
    }

    /**
     * 通过客户端心跳通道发送客户端请求
     *
     * @param nettyClientMessageCommand 发送请求到客户端
     * @return {@link Result<Void>}
     */
    @Override
    public Result<Void> sendMessage2HeartbeatClient(NettyClientMessageCommand nettyClientMessageCommand) {
        // 获取客户端ID
        String clientId = nettyClientMessageCommand.getClientId();
        String message = nettyClientMessageCommand.getMessage();
        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId);
        if (clientChannel == null) {
            return ResultFactory.errorOf("客户端：" + clientId + "不存在");
        }
        // 发送消息到客户端
        Channel channel = clientChannel.getChannel();
        NettyProxyMsg nettyProxyMsg = new NettyProxyMsg();
        nettyProxyMsg.setClientId("服务端");
        nettyProxyMsg.setData(message.getBytes(StandardCharsets.UTF_8));
        nettyProxyMsg.setType(MessageType.DISTRIBUTE_SINGLE_CLIENT_MESSAGE);
        channel.writeAndFlush(nettyProxyMsg);
        return ResultFactory.successOf();
    }
}