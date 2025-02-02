package com.wanfeng.javalearn.RPC.Netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class Server {
    public static void run(){
        // one group handle accept
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        // work group
        NioEventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap()
                    .group(boss, workerEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    // open nagle
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // open keepAlive
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // resize AllLink Queue
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            ChannelPipeline p = nioSocketChannel.pipeline();
                            p.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            p.addLast(new StringEncoder());
                            p.addLast(new StringDecoder());
                            p.addLast(new SimpleChannelInboundHandler<String>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
                                    System.out.println(s);
                                }
                            });
                        }
                    });

            ChannelFuture sync = server.bind(9999).sync();
            log.info("server running.....");
            sync.channel().closeFuture().sync();
            log.info("server stop....");
        }catch (Exception e){
            log.info("server error",e);
        }finally {
            boss.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        run();
    }
}











