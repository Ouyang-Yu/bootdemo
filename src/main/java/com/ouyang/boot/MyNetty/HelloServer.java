package com.ouyang.boot.MyNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ouyangyu369@gmail.com
 * @time 2022-05-03 19:23
 */
@Slf4j
public class HelloServer {
    public static void main(String[] args) {
        new ServerBootstrap()
                //boss 只负责服务器channel的 accept()  worker只负责客户端的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        channel.pipeline().addLast(new StringDecoder());
                        channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.debug(((String) msg));
                                ctx.fireChannelRead(msg); //传递给下一个handler
                            }
                        });

                        //将一些耗时操作放在不需要nio的group 以免影响io线程
                        channel.pipeline().addLast(new DefaultEventLoopGroup(), "handler2",
                                new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        log.debug(((String) msg));
                                    }
                                }
                        );

                    }
                })
                .bind(8888);
    }
}
