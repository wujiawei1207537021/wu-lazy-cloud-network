package wu.framework.lazy.cloud.heartbeat.client.netty.socket;


import wu.framework.lazy.cloud.heartbeat.client.netty.config.NettyServerProperties;
import wu.framework.lazy.cloud.heartbeat.common.*;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import wu.framework.lazy.cloud.heartbeat.client.netty.filter.NettyClientRealFilter;
import wu.framework.lazy.cloud.heartbeat.client.netty.filter.NettyClientVisitorRealFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户端连接真实服务
 */
@Slf4j
public class NettyClientRealSocket {
    static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    /**
     * 连接真实服务
     *
     * @param internalNetworkPenetrationRealClient 访客信息
     * @param nettyServerProperties                服务端地址信息
     */
    public static void buildRealServer(InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient,
                                       NettyServerProperties nettyServerProperties,
                                       List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) {

        buildNewRealServer(internalNetworkPenetrationRealClient, nettyServerProperties, handleChannelTypeAdvancedList);

    }

    /**
     * @param internalNetworkPenetrationRealClient 访客信息
     * @param nettyServerProperties                服务端地址信息
     */
    private static void buildNewRealServer(InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient,
                                           NettyServerProperties nettyServerProperties,
                                           List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) {
        try {
            String clientId = internalNetworkPenetrationRealClient.getClientId();
            String clientTargetIp = internalNetworkPenetrationRealClient.getClientTargetIp();
            Integer clientTargetPort = internalNetworkPenetrationRealClient.getClientTargetPort();
            Integer visitorPort = internalNetworkPenetrationRealClient.getVisitorPort();
            String visitorId = internalNetworkPenetrationRealClient.getVisitorId();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new NettyClientRealFilter());
            bootstrap.connect(clientTargetIp, clientTargetPort).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    // 客户端链接真实服务成功 设置自动读写false 等待访客连接成功后设置成true
                    Channel realChannel = future.channel();
                    realChannel.config().setOption(ChannelOption.AUTO_READ, false);

                    log.info("访客通过 客户端:【{}】,绑定本地服务,IP:{},端口:{} 新建通道成功", clientId, clientTargetIp, clientTargetPort);
                    // 客户端真实通道
                    NettyRealIdContext.pushReal(realChannel, visitorId);
                    // 绑定访客ID到当前真实通道属性
                    ChannelAttributeKeyUtils.buildVisitorId(realChannel, visitorId);
                    ChannelAttributeKeyUtils.buildClientId(realChannel, clientId);
                    ChannelAttributeKeyUtils.buildVisitorPort(realChannel, visitorPort);
                    // 通知服务端访客连接成功


                    // 新建一个通道处理
                    newVisitorConnect2Server(internalNetworkPenetrationRealClient, nettyServerProperties, handleChannelTypeAdvancedList);

                    // 是否等 服务端相应访客通道已经可以自动读写
//                    realChannel.config().setOption(ChannelOption.AUTO_READ, true);
                    // 模拟发送
                    String byteData = "GET /swagger-ui/index.html HTTP/1.1\n" +
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
//                    ChannelContext.ClientChannel clientChannel = ChannelContext.get(String.valueOf(visitorPort).getBytes(StandardCharsets.UTF_8));
//                    Channel channel = clientChannel.getChannel();
//                    channel.writeAndFlush(byteData.getBytes(StandardCharsets.UTF_8));
//                        future.channel().attr(Constant.VID).set(internalNetworkPenetrationRealClient);
//                        Constant.vrc.put(internalNetworkPenetrationRealClient, future.channel());
//                        ProxySocket.connectProxyServer(internalNetworkPenetrationRealClient);
                }else {
                    log.error("客户：【{}】,无法连接当前网络内的目标IP：【{}】,目标端口:【{}】",clientId,clientTargetIp,clientTargetPort);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 创建访客连接服务端
     *
     * @param internalNetworkPenetrationRealClient 内网穿透信息
     * @param nettyServerProperties                服务端配置信息
     * @param handleChannelTypeAdvancedList              处理器适配器
     * @throws InterruptedException 异常
     */
    protected static void newVisitorConnect2Server(InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient,
                                                   NettyServerProperties nettyServerProperties,
                                                   List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientVisitorRealFilter(new ChannelTypeAdapter(handleChannelTypeAdvancedList)))
        ;

        String inetHost = nettyServerProperties.getInetHost();
        int inetPort = nettyServerProperties.getInetPort();
        String clientId = nettyServerProperties.getClientId();


        String visitorId = internalNetworkPenetrationRealClient.getVisitorId();
        Integer visitorPort = internalNetworkPenetrationRealClient.getVisitorPort();
        String clientTargetIp = internalNetworkPenetrationRealClient.getClientTargetIp();
        Integer clientTargetPort = internalNetworkPenetrationRealClient.getClientTargetPort();

        log.info("客户端新建访客通道 连接服务端IP:{},连接服务端端口:{}", inetHost, inetPort);
        ChannelFuture future = bootstrap.connect(inetHost, inetPort);

        log.info("使用的客户端ID:" + clientId);
        future.addListener((ChannelFutureListener) futureListener -> {
            Channel channel = futureListener.channel();
            if (futureListener.isSuccess()) {

                NettyProxyMsg myMsg = new NettyProxyMsg();
                myMsg.setType(MessageType.REPORT_SINGLE_CLIENT_REAL_CONNECT);
                myMsg.setClientId(clientId);
                myMsg.setVisitorPort(visitorPort);
                myMsg.setClientTargetIp(clientTargetIp);
                myMsg.setClientTargetPort(clientTargetPort);

                myMsg.setVisitorId(visitorId);
                channel.writeAndFlush(myMsg);
                // 绑定客户端真实通信通道
                NettyCommunicationIdContext.pushVisitor(channel,visitorId);
                ChannelAttributeKeyUtils.buildVisitorId(channel, visitorId);
                ChannelAttributeKeyUtils.buildClientId(channel, clientId);
                // 客户端真实通道自动读写打开
                Channel visitor = NettyRealIdContext.getReal(visitorId);
                visitor.config().setOption(ChannelOption.AUTO_READ, true);


            } else {
                log.info("每隔2s重连....");
                // 离线
                channel.eventLoop().schedule(() -> {
                    try {
                        newVisitorConnect2Server(internalNetworkPenetrationRealClient, nettyServerProperties, handleChannelTypeAdvancedList);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 2, TimeUnit.SECONDS);
            }
        });
    }
}