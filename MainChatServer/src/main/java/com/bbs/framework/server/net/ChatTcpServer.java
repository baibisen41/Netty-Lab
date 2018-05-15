package com.bbs.framework.server.net;

import com.bbs.framework.server.AbstractServer;
import com.bbs.framework.server.coder.ChatTcpDecoder;
import com.bbs.framework.server.coder.ChatTcpEncoder;
import com.bbs.framework.server.handler.ChatTcpHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Created by baibisen on 2018/5/15.
 */
public class ChatTcpServer extends AbstractServer {

    public ChatTcpServer(int listenPort) {
        super(listenPort);
    }

    @Override
    public void pipeline(ChannelPipeline pipeline) {
        pipeline.addLast(new IdleStateHandler(300, 300, 0),
                new ChatTcpDecoder(),
                new ChatTcpEncoder(),
                new ChatTcpHandlerAdapter());
    }
}
