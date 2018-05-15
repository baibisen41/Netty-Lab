package com.bbs.framework.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

/**
 * Netty基类
 * Created by baibisen on 2018/5/15.
 */
public abstract class AbstractServer {

    private int listenPort;
    private ServerBootstrap bootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    public AbstractServer(int listenPort) {
        this.listenPort = listenPort;
    }

    public void init() {
        int workGroupCount = Runtime.getRuntime().availableProcessors() + 1;
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NioBossGroup"));
        workGroup = new NioEventLoopGroup(workGroupCount, new DefaultThreadFactory("NioWorkGroup"));
        bootstrap.group(bossGroup, workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                pipeline(ch.pipeline());
            }
        });
    }

    public abstract void pipeline(ChannelPipeline pipeline);

    public void bind() {
        try {
            bootstrap.bind(listenPort).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
    }
}
