package wu.framework.lazy.cloud.heartbeat.client.application;

/**
 * 云下心跳客户端操作nacos 配置
 */
public interface ClientNettyConfigApplication {



    /**
     * 推送客户端在线
     */
    void clientOnLine(String clientId);

    /**
     * 推送客户端离线
     */
    void clientOffLine(String clientId);

    /**
     * 暂存开启
     * @param clientId  租户ID
     */
    void stagingOpen(String clientId);


    /**
     * 暂存关闭
     * @param clientId  客户端ID 对应的租户
     */
    void stagingClose(String clientId);
}
