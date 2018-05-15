package com.bbs.framework.main;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Created by baibisen on 2018/5/15.
 */
public class FakeClientSecond {

    private static Bootstrap bootstrap;
    private static EventLoopGroup workGroup;

    public static void main(String[] args) {
        try {
            bootstrap = new Bootstrap();
            workGroup = new NioEventLoopGroup();
            bootstrap.group(workGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), new FakeClientFirstHandlerAdapter());
                }
            });
            bootstrap.connect("127.0.0.1", 8081).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class FakeClientFirstHandlerAdapter extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            System.out.println("client connect complete:" + ctx.channel().remoteAddress());
            ctx.channel().writeAndFlush("i am client2").addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("client send success");
                    } else {
                        System.out.println("client send fail");
                    }
                }
            });
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            System.out.println("client rev:" + msg.toString() + ", from:" + ctx.channel().remoteAddress());
        }

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            super.channelRegistered(ctx);
        }

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            super.exceptionCaught(ctx, cause);
            System.out.println("client throw exception:" + cause);
        }
    }
}
