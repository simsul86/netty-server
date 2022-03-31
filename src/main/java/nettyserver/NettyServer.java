package nettyserver;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

private int port;
	
	public NettyServer(int port) {
		
		this.port = port;
	}

	public static void main(String[] args) throws InterruptedException {
		
		new NettyServer(9000).run();
	}
	
	/**
	 * 서버의 port열림. 서버 오픈.
	 * 
	 * @param port
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException {
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();  
        
        try{
        	
        	ServerBootstrap bootstrap = new ServerBootstrap();
            
        	bootstrap.group(bossGroup, workerGroup);        	
            bootstrap.channel(NioServerSocketChannel.class);
            // bootstrap.option(ChannelOption.SO_BACKLOG, 100);
            bootstrap.localAddress(new InetSocketAddress(port));
            bootstrap.childHandler(new NettyServerInitializer());
        	
            // ChannelFuture channelFuture = bootstrap.bind(port).sync();
            ChannelFuture channelFuture = bootstrap.bind().sync();
            
            channelFuture.addListener(new ChannelFutureListener() {
                
            	// @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    
            		if(channelFuture.isSuccess()){
                        
            			System.out.println("Server bound");
                    } else {
                        
                    	System.out.println("Bound Attempt Failed");
                        
                    	channelFuture.cause().printStackTrace();
                    }
                }
            });
            
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        	
        	e.printStackTrace();
        } finally {
			
        	bossGroup.shutdownGracefully().sync();
        	
        	workerGroup.shutdownGracefully().sync();
		}
	}	
}