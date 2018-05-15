package com.bbs.framework.main;

import com.bbs.framework.server.net.ChatTcpServer;

/**
 * 主启动类
 * Created by baibisen on 2018/5/15.
 */
public class ChatServerStartup {

    public static void main(String[] args) {
        ChatTcpServer server = new ChatTcpServer(8081);
        try {
            server.init();
            server.bind();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
