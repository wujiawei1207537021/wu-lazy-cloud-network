package com.lazy.netty.proxy.server;

import com.lazy.netty.proxy.msg.MyMsgDecoder;
import com.lazy.netty.proxy.msg.MyMsgEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class ServerSocket {
    private static EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static EventLoopGroup workerGroup = new NioEventLoopGroup();
    private static ChannelFuture channelFuture;

    /**
     * 启动服务端
     *
     * @throws Exception
     */
    public static void startServer() throws Exception {
        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
                            pipeline.addLast(new MyMsgEncoder());
                            pipeline.addLast(new IdleStateHandler(40, 10, 0));
                            pipeline.addLast(new ClientHandler());
                        }

                    });
            channelFuture = b.bind(Constant.serverPort).sync();

            channelFuture.addListener((ChannelFutureListener) channelFuture -> {
                // 服务器已启动
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            shutdown();
            // 服务器已关闭
        }
    }

    public static void shutdown() {
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if ((bossGroup != null) && (!bossGroup.isShutdown())) {
            bossGroup.shutdownGracefully();
        }
        if ((workerGroup != null) && (!workerGroup.isShutdown())) {
            workerGroup.shutdownGracefully();
        }
    }
}