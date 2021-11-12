//Implementing the client logic with ChannelHandlers

package java_with_gradle_netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("Netty Rocks!", CharsetUtil.UTF_8));	//When notified that channel is active, send a msg
    }

    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf in) {
        System.out.println("Client received: " + in.toString(CharsetUtil.UTF_8));		//Logs a dump of the receive message
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause){
        cause.printStackTrace();
        channelHandlerContext.close();
    }
}
