//Bootstrapping the client

package java_with_gradle_netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class client {

	public static void main(String[] args) throws Exception{
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
		    Bootstrap clientBootstrap = new Bootstrap();

		    clientBootstrap.group(group)									//Specifies EventLoopGroup to handle client event; NIO implementation is needed
		    .channel(NioSocketChannel.class)								//Channel type is the one for NIO transport
		    .remoteAddress(new InetSocketAddress("localhost",9900))			//Sets the server's InetSocketAddress
		    .handler(new ChannelInitializer<SocketChannel>() {				//Adds an ClientHandler to the Pipeline when channel is created
		   
		        protected void initChannel(SocketChannel socketChannel) throws Exception {
		            socketChannel.pipeline().addLast(new ClientHandler());
		        }
		    });	
		    
		    ChannelFuture channelFuture = clientBootstrap.connect().sync(); 	//Connects to the remote peer; wait until the connect completes
		    channelFuture.channel().closeFuture().sync();						//block until the channel closes
		} finally {
		   group.shutdownGracefully().sync();									//shutdown the thread pools and the release of all resources..
		}
	}
}

