package wu.framework.lazy.cloud.heartbeat.client.netty.advanced;

import com.alibaba.fastjson.JSONObject;
import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.advanced.client.AbstractHandleDistributeConnectSuccessNotificationTypeAdvancedHandle;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import wu.framework.lazy.cloud.heartbeat.client.application.ClientNettyConfigApplication;
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
public class HandleDistributeConnectSuccessNotificationTypeAdvancedHandle extends AbstractHandleDistributeConnectSuccessNotificationTypeAdvancedHandle<NettyProxyMsg> {

    private final ClientNettyConfigApplication clientNettyConfigApplication;


    public HandleDistributeConnectSuccessNotificationTypeAdvancedHandle(ClientNettyConfigApplication clientNettyConfigApplication) {
        this.clientNettyConfigApplication = clientNettyConfigApplication;
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


        // 存储其他客户端状态
        List<String> clientIdList = JSONObject.parseArray(new String(msg.getData()), String.class);
        for (String tenantId : clientIdList) {
            clientNettyConfigApplication.clientOnLine(tenantId);
        }

    }
}
