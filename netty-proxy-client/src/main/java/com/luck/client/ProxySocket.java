package com.luck.client;
 
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
 
import com.luck.msg.MyMsg;
import com.luck.msg.MyMsgDecoder;
import com.luck.msg.MyMsgEncoder;
 
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;
 
public class ProxySocket {
	private static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
 
	/** 重连代理服务 */
	private static final ScheduledExecutorService reconnectExecutor = Executors.newSingleThreadScheduledExecutor();
 
	public static Channel connectProxyServer() throws Exception {
		reconnectExecutor.scheduleAtFixedRate(() -> {
			try {
				connectProxyServer(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 3, 3, TimeUnit.SECONDS);
		return connectProxyServer(null);
	}
 
	public static Channel connectProxyServer(String vid) throws Exception {
		if (StringUtil.isNullOrEmpty(vid)) {
			if (Constant.proxyChannel == null || !Constant.proxyChannel.isActive()) {
				newConnect(null);
			}
			return null;
		} else {
			Channel channel = Constant.vpc.get(vid);
			if (null == channel) {
				newConnect(vid);
				channel = Constant.vpc.get(vid);
			}
			return channel;
		}
	}
 
	private static void newConnect(String vid) throws InterruptedException {
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new MyMsgDecoder(Integer.MAX_VALUE, 0, 4, -4, 0));
						pipeline.addLast(new MyMsgEncoder());
						pipeline.addLast(new IdleStateHandler(40, 8, 0));
						pipeline.addLast(new ProxyHandler());
					}
				});
 
		bootstrap.connect(Constant.serverIp, Constant.serverPort).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					// 客户端链接代理服务器成功
					Channel channel = future.channel();
					if (StringUtil.isNullOrEmpty(vid)) {
						// 告诉服务端这条连接是client的连接
						MyMsg myMsg = new MyMsg();
						myMsg.setType(MyMsg.TYPE_CONNECT);
						myMsg.setData("client".getBytes());
						channel.writeAndFlush(myMsg);
 
						Constant.proxyChannel = channel;
					} else {
 
						// 告诉服务端这条连接是vid的连接
						MyMsg myMsg = new MyMsg();
						myMsg.setType(MyMsg.TYPE_CONNECT);
						myMsg.setData(vid.getBytes());
						channel.writeAndFlush(myMsg);
 
						// 客户端绑定通道关系
						Constant.vpc.put(vid, channel);
						channel.attr(Constant.VID).set(vid);
 
						Channel realChannel = Constant.vrc.get(vid);
						if (null != realChannel) {
							realChannel.config().setOption(ChannelOption.AUTO_READ, true);
						}
					}
				}
			}
		});
	}
}