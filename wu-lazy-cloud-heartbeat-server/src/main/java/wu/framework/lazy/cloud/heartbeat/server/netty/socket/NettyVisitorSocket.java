package wu.framework.lazy.cloud.heartbeat.server.netty.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import wu.framework.lazy.cloud.heartbeat.common.InternalNetworkPenetrationRealClient;
import wu.framework.lazy.cloud.heartbeat.common.NettyVisitorPortContext;
import wu.framework.lazy.cloud.heartbeat.common.adapter.ChannelFlowAdapter;
import wu.framework.lazy.cloud.heartbeat.server.netty.filter.VisitorFilter;

/**
 * 访客链接socket
 */
@Slf4j
public class NettyVisitorSocket {
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final VisitorFilter visitorFilter;

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

        Channel visitor = NettyVisitorPortContext.getVisitor(visitorPort);
        if (visitor == null) {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(visitorFilter);
            ChannelFuture sync = b.bind(visitorPort).sync();
            sync.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    Channel channel = future.channel();
                    log.info("访客端口：{} 开启", visitorPort);
                    NettyVisitorPortContext.pushVisitor(visitorPort, channel);
                }
            });

        } else {
            log.warn("访客端口:{} 重复启动", visitorPort);
        }

    }

    public static final class NettyVisitorSocketBuilder {

        /**
         * 客户端ID
         */
        private String clientId;

        /**
         * 客户端目标地址
         */
        private String clientTargetIp;

        /**
         * 客户端目标端口
         */
        private Integer clientTargetPort;


        /**
         * 访问端口
         */
        private Integer visitorPort;
        /**
         * 访客ID
         */
        private String visitorId;

        /**
         * 流量适配器
         */
        private ChannelFlowAdapter channelFlowAdapter;

        public static NettyVisitorSocketBuilder builder() {
            return new NettyVisitorSocketBuilder();
        }

        /**
         * 填充客户端
         *
         * @param clientId 客户端
         * @return 返回当前对象
         */
        public NettyVisitorSocketBuilder builderClientId(String clientId) {
            this.clientId = clientId;
            return this;
        }

        /**
         * 绑定客户端目标IP
         *
         * @param clientTargetIp 客户端目标IP
         * @return 当前对象
         */
        public NettyVisitorSocketBuilder builderClientTargetIp(String clientTargetIp) {
            this.clientTargetIp = clientTargetIp;
            return this;
        }

        /**
         * 绑定客户端目标端口
         *
         * @param clientTargetPort 客户端目标端口
         * @return 当前对象
         */
        public NettyVisitorSocketBuilder builderClientTargetPort(Integer clientTargetPort) {
            this.clientTargetPort = clientTargetPort;
            return this;
        }

        /**
         * 绑定访客端口
         *
         * @param visitorPort 访客端口
         * @return 当前对象
         */
        public NettyVisitorSocketBuilder builderVisitorPort(Integer visitorPort) {
            this.visitorPort = visitorPort;
            return this;
        }

        /**
         * 绑定流量适配器
         *
         * @param channelFlowAdapter 流量适配器
         * @return 当前对象
         */
        public NettyVisitorSocketBuilder builderChannelFlowAdapter(ChannelFlowAdapter channelFlowAdapter) {
            this.channelFlowAdapter = channelFlowAdapter;
            return this;
        }

        /**
         * 绑定访客ID
         *
         * @param visitorId 访客ID
         * @return 当前对象
         */
        public NettyVisitorSocketBuilder builderVisitorId(String visitorId) {
            this.visitorId = visitorId;
            return this;
        }

        public NettyVisitorSocket build() {
            if (clientId == null) {
                throw new IllegalArgumentException("clientId must not null");
            }
            if (clientTargetIp == null) {
                throw new IllegalArgumentException("clientTargetIp must not null");
            }
            if (clientTargetPort == null) {
                throw new IllegalArgumentException("clientTargetPort must not null");
            }
            if (visitorPort == null) {
                throw new IllegalArgumentException("visitorPort must not null");
            }
            InternalNetworkPenetrationRealClient internalNetworkPenetrationRealClient = InternalNetworkPenetrationRealClient
                    .builder()
                    .clientId(clientId)
                    .clientTargetIp(clientTargetIp)
                    .clientTargetPort(clientTargetPort)
                    .visitorPort(visitorPort)
                    .visitorId(visitorId).build();

            VisitorFilter visitorFilter = new VisitorFilter(internalNetworkPenetrationRealClient, channelFlowAdapter);
            return new NettyVisitorSocket(visitorFilter);
        }


    }

}