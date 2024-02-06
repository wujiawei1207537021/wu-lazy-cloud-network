package wu.framework.lazy.cloud.heartbeat.server.application;

/**
 * 云下心跳客户端操作nacos 配置
 */
public interface ServerNettyConfigApplication {


    /**
     * 客户端在线
     *
     * @param clientId 客户端ID
     */
    void clientOnLine(String clientId);

    /**
     * 客户端离线
     *
     * @param clientId 客户端ID
     */
    void clientOffLine(String clientId);

    /**
     * 客户端暂存关闭
     *
     * @param clientId 客户端ID
     */
    void stagingClosed(String clientId);


    /**
     * 客户端暂存开启
     *
     * @param clientId 客户端ID
     */
    void stagingOpened(String clientId);

}
