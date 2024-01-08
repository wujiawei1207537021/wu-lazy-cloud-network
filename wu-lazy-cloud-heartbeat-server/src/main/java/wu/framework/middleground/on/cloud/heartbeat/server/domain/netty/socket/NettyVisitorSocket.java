package wu.framework.middleground.on.cloud.heartbeat.server.domain.netty.socket;

import wu.framework.lazy.cloud.heartbeat.common.NettyVisitorContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import wu.framework.middleground.on.cloud.heartbeat.server.domain.netty.filter.VisitorFilter;

/**
 * 访客链接socket
 */
@Slf4j
public class NettyVisitorSocket {
    private final VisitorFilter visitorFilter;
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public NettyVisitorSocket(VisitorFilter visitorFilter) {
        this.visitorFilter = visitorFilter;
    }

    /**
     * 启动服务代理
     *
     * @param visitorPort 访客代理端口
     * @throws Exception
     */
    public void startServer(int visitorPort) throws Exception {

        Channel visitor = NettyVisitorContext.getVisitor(visitorPort);
        if (visitor == null) {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(visitorFilter);
            ChannelFuture sync = b.bind(visitorPort).sync();
            sync.addListener((ChannelFutureListener) future -> {
                if(future.isSuccess()){
                    Channel channel = future.channel();
                    log.info("访客端口：{} 开启", visitorPort);
                    NettyVisitorContext.pushVisitor(visitorPort, channel);
                }
            });

        } else {
            log.warn("访客端口:{} 重复启动", visitorPort);
        }

    }

}