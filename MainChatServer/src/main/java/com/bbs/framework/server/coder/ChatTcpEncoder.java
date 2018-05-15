package com.bbs.framework.server.coder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

import java.util.List;

/**
 * Created by baibisen on 2018/5/15.
 */
public class ChatTcpEncoder extends StringEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        super.encode(ctx, msg, out);
    }
}
