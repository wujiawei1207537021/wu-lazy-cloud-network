package com.luck.server;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerStart {
    public static void main(String[] args) throws Exception {
        if (null != args && args.length == 2) {
            int visitorPort = Integer.parseInt(args[1]);
            int serverPort = Integer.parseInt(args[0]);
            Constant.visitorPort = visitorPort;
            Constant.serverPort = serverPort;
        }
        log.info(" 服务代理端口: " + Constant.visitorPort + " 服务端端口:" + Constant.serverPort);
        // 启动访客服务端，用于接收访客请求
        VisitorSocket.startServer();
        // 启动代理服务端，用于接收客户端请求
        ServerSocket.startServer();
    }
}