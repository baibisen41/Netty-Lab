package com.bbs.framework.server.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;

import java.util.List;

/**
 * Created by baibisen on 2018/5/15.
 */
public class ChatTcpDecoder extends StringDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        super.decode(ctx, msg, out);
    }
}
