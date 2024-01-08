package wu.framework.lazy.cloud.heartbeat.server.netty.socket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import wu.framework.lazy.cloud.heartbeat.server.netty.filter.NettyServerFilter;

public class NettyOnCloudNettyServerSocket {
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private ChannelFuture channelFuture;
    private final NettyServerFilter nettyServerFilter;// 通道业务处理

    public NettyOnCloudNettyServerSocket(NettyServerFilter nettyServerFilter) {
        this.nettyServerFilter = nettyServerFilter;
    }

    /**
     * 启动服务端
     *
     * @throws Exception
     */
    public void startServer(int serverPort) throws Exception {
        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    // 给服务端channel设置属性
                    .option(ChannelOption.SO_BACKLOG, 128)

                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(nettyServerFilter)
            ;
            channelFuture = b.bind(serverPort).sync();

            channelFuture.addListener((ChannelFutureListener) channelFuture -> {
                // 服务器已启动
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            shutdown();
            // 服务器已关闭
        }
    }

    public void shutdown() {
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