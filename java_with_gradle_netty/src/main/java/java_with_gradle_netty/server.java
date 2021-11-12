//Bootstrapping the server
package java_with_gradle_netty;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class server {
	
	public static void main(String args[]) throws Exception {
		
		EventLoopGroup group = new NioEventLoopGroup();
		
		try{
		    ServerBootstrap bootstrap = new ServerBootstrap();
		    bootstrap.group(group);
		    bootstrap.channel(NioServerSocketChannel.class);						//Specifies the use of an NIO transport channel
		    bootstrap.localAddress(new InetSocketAddress("localhost",9900));		//sets the socket address

		    bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {			//Adds an HelloServerHandler to the channel's ChannelPipeline
		        protected void initChannel(SocketChannel socketChannel) throws Exception {
		            socketChannel.pipeline().addLast(new HelloServerHandler());
		        }
		    });			
		    ChannelFuture channelFuture = bootstrap.bind().sync();		//Binds the server asynchronously; sync() wait for the bind to complete
		    channelFuture.channel().closeFuture().sync();				//Gets the CloseFuture of the channel and blocks the current thread until it's complete
		} catch(Exception e){
		    e.printStackTrace();
		} 
		finally {
				group.shutdownGracefully().sync();				//releasing all the resources
		}
	}
}

