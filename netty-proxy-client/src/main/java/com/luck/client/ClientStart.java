package com.luck.client;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientStart {

    public static void main(String[] args) throws Exception {
        if (null != args && args.length == 3) {
            int realPort = Integer.parseInt(args[2]);
            int serverPort = Integer.parseInt(args[1]);
            String serverIp = args[0];
            Constant.serverIp = serverIp;
            Constant.serverPort = serverPort;
            Constant.realPort = realPort;
        }
        log.info(" 服务端地址: " + Constant.serverIp + " 服务端端口:" + Constant.serverPort + "\n 真实端口: " + Constant.realPort);
        // 连接代理服务
        ProxySocket.connectProxyServer();
    }
}