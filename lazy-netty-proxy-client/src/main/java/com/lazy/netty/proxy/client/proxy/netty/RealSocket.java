package com.lazy.netty.proxy.client.proxy.netty;

import com.lazy.netty.proxy.client.proxy.handler.RealHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.StringUtil;

public class RealSocket {
    static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    /**
     * 连接真实服务
     *
     * @param vid 访客ID
     * @return
     */
    public static Channel connectRealServer(String vid) {
        if (StringUtil.isNullOrEmpty(vid)) {
            return null;
        }
        Channel channel = Constant.vrc.get(vid);
        if (null == channel) {
            newConnect(vid);
            channel = Constant.vrc.get(vid);
        }
        return channel;
    }

    private static void newConnect(String vid) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new RealHandler());
                        }

                    });
            bootstrap.connect("127.0.0.1", Constant.realPort).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        // 客户端链接真实服务成功
                        future.channel().config().setOption(ChannelOption.AUTO_READ, false);
                        future.channel().attr(Constant.VID).set(vid);
                        Constant.vrc.put(vid, future.channel());
                        ProxySocket.connectProxyServer(vid);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}