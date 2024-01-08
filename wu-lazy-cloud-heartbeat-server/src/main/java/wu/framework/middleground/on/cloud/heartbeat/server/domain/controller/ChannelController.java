package wu.framework.middleground.on.cloud.heartbeat.server.domain.controller;



import com.wu.framework.inner.layer.web.EasyController;
import com.wu.framework.response.Result;
import com.wu.framework.response.ResultFactory;
import io.netty.channel.Channel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wu.framework.lazy.cloud.heartbeat.common.ChannelContext;
import wu.framework.lazy.cloud.heartbeat.common.InternalNetworkPenetrationRealClient;
import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 通道管理
 */
@Slf4j
@Tag(name = "服务端Netty通道管理")
@EasyController("/channel")
public class ChannelController {

    @Operation(summary = "服务端访问客户端")
    @GetMapping("/{clientId}")
    public void fetchClientId(@PathVariable("clientId") String clientId) {
        log.info("clientId:" + clientId);
        // 获取客户端channel
        ChannelContext.ClientChannel clientChannel = ChannelContext.get(clientId.getBytes(StandardCharsets.UTF_8));

        if (clientChannel == null) {
            if (log.isDebugEnabled()) {
                for (ChannelContext.ClientChannel exisitClientChannel : ChannelContext.get()) {
                    log.debug("当前存在的通道:{}", new String(exisitClientChannel.getClientId()));
                }
            }
            return;
        }
        //  发送消息
        Channel channel = clientChannel.getChannel();
        NettyProxyMsg nettyMsg = new NettyProxyMsg();
        nettyMsg.setClientId(clientId);
        // 下发 客户端消息
        nettyMsg.setType(MessageType.DISTRIBUTE_CLIENT_TRANSFER);

        channel.writeAndFlush(nettyMsg);
    }

    /**
     * 获取当前服务端所有通道
     */
    @Operation(summary = "获取当前服务端所有通道")
    @GetMapping("/findClientIdList")
    public Result<List<String>> findClientIdList() {
        List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
        return ResultFactory.successOf(clientChannels.stream().map(clientChannel -> new String(clientChannel.getClientId())).toList());
    }

    /**
     * 批量调度客户端 数据转换接收
     */
    @Operation(summary = "批量调度客户端 数据转换接收")
    @PostMapping("/batchTransfer")
    public Result<List<String>> batchTransfer(@RequestBody InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient) {

        String data = "GET /swagger-ui/index.html HTTP/1.1\n" +
                "Host: 127.0.0.1:19080\n" +
                "Connection: keep-alive\n" +
                "Cache-Control: max-age=0\n" +
                "sec-ch-ua: \"Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120\"\n" +
                "sec-ch-ua-mobile: ?0\n" +
                "sec-ch-ua-platform: \"macOS\"\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-User: ?1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "Cookie: XXL_JOB_LOGIN_IDENTITY=7b226964223a312c22757365726e616d65223a2261646d696e222c2270617373776f7264223a226531306164633339343962613539616262653536653035376632306638383365222c22726f6c65223a312c227065726d697373696f6e223a6e756c6c7d; Hm_lvt_173e771eef816c412396d2cb4fe2d632=1703040917\n";

        List<ChannelContext.ClientChannel> clientChannels = ChannelContext.get();
        String clientTargetIp = internalNetworkPenetrationRealClient.getClientTargetIp();
        Integer clientTargetPort = internalNetworkPenetrationRealClient.getClientTargetPort();
        Integer visitorPort = internalNetworkPenetrationRealClient.getVisitorPort();
        for (ChannelContext.ClientChannel clientChannel : clientChannels) {
            Channel channel = clientChannel.getChannel();
            NettyProxyMsg nettyProxyMsg = new NettyProxyMsg();
            // 下发 客户端消息
            nettyProxyMsg.setType(MessageType.DISTRIBUTE_CLIENT_TRANSFER);
            nettyProxyMsg.setClientId(clientChannel.getClientId());
            nettyProxyMsg.setVisitorPort(visitorPort);
            nettyProxyMsg.setClientTargetIp(clientTargetIp);
            nettyProxyMsg.setClientTargetPort(clientTargetPort);
            nettyProxyMsg.setData(data.getBytes(StandardCharsets.UTF_8));
            channel.writeAndFlush(nettyProxyMsg);
        }
        return ResultFactory.successOf();
    }

}
