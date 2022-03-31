package nettyserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {

		// ChannelPipeline channelPipeline = channel.pipeline();
		// TODO : decoder
		// TODO : encoder
		// channelPipeline.addLast(new nettyServerHandler());
		channel.pipeline().addLast(new LineBasedFrameDecoder(64*1024))
						  .addLast(new StringDecoder())
						  .addLast(new StringEncoder())
						  .addLast(new NettyServerHandler());
	}
}
