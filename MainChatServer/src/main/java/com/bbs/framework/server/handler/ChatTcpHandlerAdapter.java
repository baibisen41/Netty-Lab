package com.bbs.framework.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * Created by baibisen on 2018/5/15.
 */
public class ChatTcpHandlerAdapter extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("new Client:" + ctx.channel().remoteAddress());
        ctx.channel().writeAndFlush("i am server").addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("server send success");
                } else {
                    System.out.println("server send fail");
                }
            }
        });
    }

    // 处理输入流
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (msg instanceof ByteBuf) {
            String msgStr = ((ByteBuf) msg).toString(CharsetUtil.UTF_8);
            if ("heart".equals(msgStr)) {
                System.out.println("heart from:" + ctx.channel().remoteAddress());
            }
        } else {
            System.out.println("server rev:" + msg.toString() + "; from:" + ctx.channel().remoteAddress());
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("drop Client:" + ctx.channel().remoteAddress());
    }

    // 检测客户端发送心跳 通过IdleStateHandler设置检测心跳间隔
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        System.out.println("5s没有收到该客户端消息了：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("throw exception:" + cause);
    }
}
