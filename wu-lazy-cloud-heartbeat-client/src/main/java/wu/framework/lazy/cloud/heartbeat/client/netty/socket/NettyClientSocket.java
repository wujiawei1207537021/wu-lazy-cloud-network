package wu.framework.lazy.cloud.heartbeat.client.netty.socket;


import wu.framework.lazy.cloud.heartbeat.common.MessageType;
import wu.framework.lazy.cloud.heartbeat.common.NettyProxyMsg;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelTypeAdapter;
import wu.framework.lazy.cloud.heartbeat.common.advanced.HandleChannelTypeAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.utils.ChannelAttributeKeyUtils;
import wu.framework.lazy.cloud.heartbeat.client.application.ClientNettyConfigApplication;
import wu.framework.lazy.cloud.heartbeat.client.netty.filter.NettyClientFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 客户端连接服务端
 */
@Slf4j
public class NettyClientSocket {
    /**
     * 服务端host
     */
    private final String inetHost;
    /**
     * 服务端端口
     */
    private final int inetPort;
    /**
     * 当前客户端id
     */
    @Getter
    private final String clientId;
    /**
     * nacos配置信息处理应用
     */
    @Getter
    private final ClientNettyConfigApplication clientNettyConfigApplication;

    private final List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList; // 处理服务端发送过来的数据类型

    private static final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public NettyClientSocket(String inetHost, int inetPort, String clientId, ClientNettyConfigApplication clientNettyConfigApplication, List<HandleChannelTypeAdvanced> handleChannelTypeAdvancedList) {
        this.inetHost = inetHost;
        this.inetPort = inetPort;
        this.clientId = clientId;
        this.clientNettyConfigApplication = clientNettyConfigApplication;
        this.handleChannelTypeAdvancedList = handleChannelTypeAdvancedList;
    }

    public void newConnect2Server() throws InterruptedException {
        newConnect2Server(inetHost, inetPort, clientId, clientNettyConfigApplication);
    }

    protected void newConnect2Server(String inetHost, int inetPort, String clientId, ClientNettyConfigApplication clientNettyConfigApplication) throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientFilter(new ChannelTypeAdapter(handleChannelTypeAdvancedList),this))
        ;

        log.info("连接服务端IP:{},连接服务端端口:{}", inetHost, inetPort);
        ChannelFuture future = bootstrap.connect(inetHost, inetPort);
        Channel channel = future.channel();

        log.info("使用的租户ID:" + clientId);
        future.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {

                log.info("连接服务端成功");
                // 告诉服务端这条连接是client的连接
                NettyProxyMsg nettyMsg = new NettyProxyMsg();
                nettyMsg.setType(MessageType.REPORT_CLIENT_CONNECT_SUCCESS);
                nettyMsg.setClientId(clientId);
                nettyMsg.setData(( clientId ).getBytes());
                ChannelAttributeKeyUtils.buildClientId(channel,clientId);
                channel.writeAndFlush(nettyMsg);
                // 在线
                clientNettyConfigApplication.clientOnLine(clientId);
            } else {
                log.info("每隔2s重连....");
                // 离线
                clientNettyConfigApplication.clientOffLine(clientId);
                futureListener.channel().eventLoop().schedule(() -> {
                    try {
                        newConnect2Server(inetHost, inetPort, clientId, clientNettyConfigApplication);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 2, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 关闭连接
     */

    public void shutdown() {
        if ((eventLoopGroup != null) && (!eventLoopGroup.isShutdown())) {
            eventLoopGroup.shutdownGracefully();
        }
    }

}