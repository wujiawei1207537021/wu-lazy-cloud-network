package wu.framework.lazy.cloud.heartbeat.server.netty.advanced;


import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.server.AbstractHandleClientConnectSuccessTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import wu.framework.lazy.cloud.heartbeat.server.application.InternalNetworkPenetrationMappingApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.NettyClientBlacklistApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.ServerNettyConfigApplication;
import wu.framework.lazy.cloud.heartbeat.server.model.netty.client.blacklist.NettyClientBlacklist;

import java.nio.charset.StandardCharsets;
import java.util.List;


/**
 * 服务端连接成功处理
 * REPORT_CLIENT_CONNECT_SUCCESS
 */
@Slf4j
@Component
public class ServerHandleClientConnectSuccessTypeAdvanced extends AbstractHandleClientConnectSuccessTypeAdvanced<NettyProxyMsg> {

    private final ServerNettyConfigApplication serverNettyConfigApplication;
    private final NettyClientBlacklistApplication nettyClientBlacklistApplication;
    private final InternalNetworkPenetrationMappingApplication internalNetworkPenetrationMappingApplication;

    public ServerHandleClientConnectSuccessTypeAdvanced(ServerNettyConfigApplication serverNettyConfigApplication, NettyClientBlacklistApplication nettyClientBlacklistApplication, InternalNetworkPenetrationMappingApplication internalNetworkPenetrationMappingApplication) {
        this.serverNettyConfigApplication = serverNettyConfigApplication;
        this.nettyClientBlacklistApplication = nettyClientBlacklistApplication;
        this.internalNetworkPenetrationMappingApplication = internalNetworkPenetrationMappingApplication;
    }


    /**
     * 处理当前数据
     *
     * @param newChannel 当前通道
     * @param msg        通道数据
     */
    @Override
    public void doHandler(Channel newChannel, NettyProxyMsg msg) {


        String clientId = new String(msg.getClientId());
        ChannelContext.push(newChannel, clientId);

        ChannelAttributeKeyUtils.buildClientId(newChannel, clientId);
        log.info("客户端:{}，IP:{}连接成功", new String(msg.getClientId()), newChannel.remoteAddress().toString());
        // 验证客户端是否时黑名单
        NettyClientBlacklist nettyClientBlacklist = new NettyClientBlacklist();
        nettyClientBlacklist.setClientId(clientId);
        nettyClientBlacklist.setIsDeleted(false);
        nettyClientBlacklistApplication.exists(nettyClientBlacklist).accept(exists -> {
            if (!exists) {
                // 服务状态在线
                serverNettyConfigApplication.clientOnLine(clientId);
                List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
                log.info("当前在线客户端数量:{}", clientChannels.size());
                // 所有的客户端ID
                List<String> clientIdList = clientChannels.stream().map(clientChannel1 -> new String(clientChannel1.getClientId())).toList();

                // TODO 多副本本地channel 无法共享问题
                // 通知所有客户端有人上线了
                for (ChannelContext.ClientChannel clientChannel : clientChannels) {
                    Channel channel = clientChannel.getChannel();
                    NettyProxyMsg nettyMsg = new NettyProxyMsg();
                    nettyMsg.setType(MessageType.DISTRIBUTE_CLIENT_CONNECTION_SUCCESS_NOTIFICATION);
                    nettyMsg.setData((JSON.toJSONString(clientIdList)
                            .getBytes(StandardCharsets.UTF_8)));
                    // 发送所有客户端ID
                    channel.writeAndFlush(nettyMsg);
                }
                log.info("开始开启客户端：【{}】,端口映射", clientId);
                // 创建访问者（内网穿透连接创建）
                internalNetworkPenetrationMappingApplication.createVisitor(clientId);
                log.info("结束开启客户端：【{}】,端口映射", clientId);

            } else {
                // 黑名单客户端

            }
        });


    }

}
