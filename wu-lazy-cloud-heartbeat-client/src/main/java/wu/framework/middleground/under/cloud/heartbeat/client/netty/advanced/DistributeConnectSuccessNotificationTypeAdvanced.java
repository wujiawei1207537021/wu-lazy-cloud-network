package wu.framework.middleground.under.cloud.heartbeat.client.netty.advanced;

import com.alibaba.fastjson.JSONObject;
import wu.framework.middleground.cloud.heartbeat.common.ChannelContext;
import wu.framework.middleground.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.middleground.cloud.heartbeat.common.advanced.client.AbstractDistributeConnectSuccessNotificationTypeAdvanced;
import wu.framework.middleground.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import wu.framework.middleground.under.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import wu.framework.middleground.under.cloud.heartbeat.client.netty.config.NettyServerProperties;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 客户端连接成功通知
 */
@Slf4j
@Component
public class DistributeConnectSuccessNotificationTypeAdvanced extends AbstractDistributeConnectSuccessNotificationTypeAdvanced <NettyProxyMsg>{

    private final ClientNettyConfigApplication clientNettyConfigApplication;
    private final NettyServerProperties nettyServerProperties;

    public DistributeConnectSuccessNotificationTypeAdvanced(ClientNettyConfigApplication clientNettyConfigApplication, NettyServerProperties nettyServerProperties) {
        this.clientNettyConfigApplication = clientNettyConfigApplication;
        this.nettyServerProperties = nettyServerProperties;
    }

    /**
     * 处理当前数据
     *
     * @param channel 当前通道
     * @param msg     通道数据
     */
    @Override
    protected void doHandler(Channel channel, NettyProxyMsg msg) {
        log.warn("客户端ID：{},客户端:{}连接成功", new String(msg.getClientId()), new String(msg.getData()));

        // 缓存当前通道
        String clientId = nettyServerProperties.getClientId();
        NettyProxyMsg nettyMsg = new NettyProxyMsg();
        nettyMsg.setClientId(clientId.getBytes(StandardCharsets.UTF_8));
        ChannelContext.push(channel, nettyMsg);
        ChannelAttributeKeyUtils.buildClientId(channel,clientId);
        // 存储其他客户端状态
        List<String> clientIdList = JSONObject.parseArray(new String(msg.getData()), String.class);
        for (String tenantId : clientIdList) {
            clientNettyConfigApplication.clientOnLine(tenantId);
        }

    }
}
