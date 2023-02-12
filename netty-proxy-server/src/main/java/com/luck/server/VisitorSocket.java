package com.luck.server;
 
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
 
public class VisitorSocket {
	private static EventLoopGroup bossGroup = new NioEventLoopGroup();
	private static EventLoopGroup workerGroup = new NioEventLoopGroup();
 
	/**
	 * 启动服务代理
	 * 
	 * @throws Exception
	 */
	public static void startServer() throws Exception {
 
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pipeline = ch.pipeline();
						pipeline.addLast(new ChannelDuplexHandler());
						pipeline.addLast(new VisitorHandler());
					}
				});
		b.bind(Constant.visitorPort).get();
 
	}
 
}