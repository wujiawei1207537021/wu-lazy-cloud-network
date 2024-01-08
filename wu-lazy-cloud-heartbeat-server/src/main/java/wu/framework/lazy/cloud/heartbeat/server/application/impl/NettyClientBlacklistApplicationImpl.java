package wu.framework.lazy.on.cloud.heartbeat.server.domain.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.NettyClientBlacklistApplication;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.assembler.NettyClientBlacklistDTOAssembler;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.command.netty.client.blacklist.*;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.application.dto.NettyClientBlacklistDTO;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.model.netty.client.blacklist.NettyClientBlacklist;
import wu.framework.lazy.on.cloud.heartbeat.server.domain.model.netty.client.blacklist.NettyClientBlacklistRepository;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
/**
 * describe 客户端黑名单 
 *
 * @author Jia wei Wu
 * @date 2023/12/27 03:46 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl 
 **/
@LazyApplication
public class NettyClientBlacklistApplicationImpl implements NettyClientBlacklistApplication {

    @Resource
    NettyClientBlacklistRepository nettyClientBlacklistRepository;
    /**
     * describe 新增客户端黑名单
     *
     * @param nettyClientBlacklistStoryCommand 新增客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单新增后领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklist> story(NettyClientBlacklistStoryCommand nettyClientBlacklistStoryCommand) {
        NettyClientBlacklist nettyClientBlacklist = NettyClientBlacklistDTOAssembler.INSTANCE.toNettyClientBlacklist(nettyClientBlacklistStoryCommand);
        // 添加和名单，客户端下线
        Result<NettyClientBlacklist> story = nettyClientBlacklistRepository.story(nettyClientBlacklist);
        // 获取客户端channel 发送下下通知
        String clientId = nettyClientBlacklist.getClientId();
        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId.getBytes(StandardCharsets.UTF_8));
        if(null!=clientChannel){
            // 模拟客户端发送下线通知
            Channel channel = clientChannel.getChannel();
            NettyProxyMsg nettyMsg = new NettyProxyMsg();
            nettyMsg.setClientId(clientId);
            nettyMsg.setType(MessageType.REPORT_CLIENT_DISCONNECTION);

            channel.writeAndFlush(nettyMsg);
        }

        return story;
    }
    /**
     * describe 批量新增客户端黑名单
     *
     * @param nettyClientBlacklistStoryCommandList 批量新增客户端黑名单     
     * @return {@link Result<List<NettyClientBlacklist>>} 客户端黑名单新增后领域对象集合     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientBlacklist>> batchStory(List<NettyClientBlacklistStoryCommand> nettyClientBlacklistStoryCommandList) {
        List<NettyClientBlacklist> nettyClientBlacklistList = nettyClientBlacklistStoryCommandList.stream().map( NettyClientBlacklistDTOAssembler.INSTANCE::toNettyClientBlacklist).collect(Collectors.toList());
        return nettyClientBlacklistRepository.batchStory(nettyClientBlacklistList);
    }
    /**
     * describe 更新客户端黑名单
     *
     * @param nettyClientBlacklistUpdateCommand 更新客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单领域对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklist> updateOne(NettyClientBlacklistUpdateCommand nettyClientBlacklistUpdateCommand) {
        NettyClientBlacklist nettyClientBlacklist = NettyClientBlacklistDTOAssembler.INSTANCE.toNettyClientBlacklist(nettyClientBlacklistUpdateCommand);
        return nettyClientBlacklistRepository.story(nettyClientBlacklist);
    }

    /**
     * describe 查询单个客户端黑名单
     *
     * @param nettyClientBlacklistQueryOneCommand 查询单个客户端黑名单     
     * @return {@link Result<NettyClientBlacklistDTO>} 客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklistDTO> findOne(NettyClientBlacklistQueryOneCommand nettyClientBlacklistQueryOneCommand) {
        NettyClientBlacklist nettyClientBlacklist = NettyClientBlacklistDTOAssembler.INSTANCE.toNettyClientBlacklist(nettyClientBlacklistQueryOneCommand);
        return nettyClientBlacklistRepository.findOne(nettyClientBlacklist).convert(NettyClientBlacklistDTOAssembler.INSTANCE::fromNettyClientBlacklist);
    }

    /**
     * describe 查询多个客户端黑名单
     *
     * @param nettyClientBlacklistQueryListCommand 查询多个客户端黑名单     
     * @return {@link Result<List<NettyClientBlacklistDTO>>} 客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<List<NettyClientBlacklistDTO>> findList(NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand) {
        NettyClientBlacklist nettyClientBlacklist = NettyClientBlacklistDTOAssembler.INSTANCE.toNettyClientBlacklist(nettyClientBlacklistQueryListCommand);
        return nettyClientBlacklistRepository.findList(nettyClientBlacklist)        .convert(nettyClientBlacklists -> nettyClientBlacklists.stream().map(NettyClientBlacklistDTOAssembler.INSTANCE::fromNettyClientBlacklist).collect(Collectors.toList())) ;
    }

    /**
     * describe 分页查询多个客户端黑名单
     *
     * @param nettyClientBlacklistQueryListCommand 分页查询多个客户端黑名单     
     * @return {@link Result<LazyPage<NettyClientBlacklistDTO>>} 分页客户端黑名单DTO对象     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<LazyPage<NettyClientBlacklistDTO>> findPage(int size,int current,NettyClientBlacklistQueryListCommand nettyClientBlacklistQueryListCommand) {
        NettyClientBlacklist nettyClientBlacklist = NettyClientBlacklistDTOAssembler.INSTANCE.toNettyClientBlacklist(nettyClientBlacklistQueryListCommand);
        return nettyClientBlacklistRepository.findPage(size,current,nettyClientBlacklist)        .convert(page -> page.convert(NettyClientBlacklistDTOAssembler.INSTANCE::fromNettyClientBlacklist))            ;
    }

    /**
     * describe 删除客户端黑名单
     *
     * @param nettyClientBlacklistRemoveCommand 删除客户端黑名单     
     * @return {@link Result<NettyClientBlacklist>} 客户端黑名单     
     
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/

    @Override
    public Result<NettyClientBlacklist> remove(NettyClientBlacklistRemoveCommand nettyClientBlacklistRemoveCommand) {
     NettyClientBlacklist nettyClientBlacklist = NettyClientBlacklistDTOAssembler.INSTANCE.toNettyClientBlacklist(nettyClientBlacklistRemoveCommand);
     return nettyClientBlacklistRepository.remove(nettyClientBlacklist);
    }

    /**
     * describe 是否存在客户端黑名单
     *
     * @param nettyClientBlacklist 是否存在客户端黑名单
     * @return {@link Result<Boolean>} 客户端黑名单是否存在
     * @author Jia wei Wu
     * @date 2023/12/27 03:46 下午
     **/
    @Override
    public Result<Boolean> exists(NettyClientBlacklist nettyClientBlacklist) {
        return nettyClientBlacklistRepository.exists(nettyClientBlacklist);
    }
}