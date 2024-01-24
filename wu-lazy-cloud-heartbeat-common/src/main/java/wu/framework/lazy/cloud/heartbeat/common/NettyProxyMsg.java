package wu.framework.lazy.cloud.heartbeat.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

/**
 * netty 代理请求数据
 */
@NoArgsConstructor
@Setter
@Getter
public class NettyProxyMsg {
    // body 长度 type 1  clientId 4  clientTargetIp 4 clientTargetPort 4 visitorPort 4  visitorId 4 data 4
    public static final int bodyLength = 1 + 4 + 4 + 4 + 4 + 4 + 4;


    /**
     * 数据类型
     *
     * @see MessageType
     * byte长度 1
     */
    private byte type;

    /**
     * 客户端ID
     * byte[] 长度 4
     */
    private byte[] clientId;
    /**
     * 客户端目标地址
     * byte[] 长度 4
     */
    private byte[] clientTargetIp;

    /**
     * 客户端目标端口
     * byte[] 长度 4
     */
    private byte[] clientTargetPort;
    /**
     * 客户端目使用的代理端口
     * byte[] 长度 4
     */
    private byte[] visitorPort;
    /**
     * 访客ID
     * byte[] 长度 4
     */
    private byte[] visitorId;
    /**
     * 消息传输数据
     * byte[] 长度 4
     */
    private byte[] data;


    @Override
    public String toString() {
        return "NettyProxyMsg [type=" + type +
                ",clientId=" + (clientId == null ? null : new String(clientId)) +
                ",clientTargetIp=" + (clientTargetIp == null ? null : new String(clientTargetIp)) +
                ",clientTargetPort=" + (clientTargetPort == null ? null : new String(clientTargetPort)) +
                ",visitorPort=" + (visitorPort == null ? null : new String(visitorPort)) +
                "]";
    }

    public void setClientId(byte[] clientId) {
        this.clientId = clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId.getBytes(StandardCharsets.UTF_8);
    }


    public void setClientTargetIp(byte[] clientTargetIp) {
        this.clientTargetIp = clientTargetIp;
    }

    public void setClientTargetIp(String clientTargetIp) {
        this.clientTargetIp = clientTargetIp.getBytes(StandardCharsets.UTF_8);
    }

    public void setClientTargetPort(Integer clientTargetPort) {
        this.clientTargetPort = String.valueOf(clientTargetPort).getBytes(StandardCharsets.UTF_8);
    }

    public void setClientTargetPort(byte[] clientTargetPort) {
        this.clientTargetPort = clientTargetPort;
    }

    public void setVisitorPort(byte[] visitorPort) {
        this.visitorPort = visitorPort;
    }

    public void setVisitorPort(Integer visitorPort) {
        this.visitorPort = String.valueOf(visitorPort).getBytes(StandardCharsets.UTF_8);
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId.getBytes(StandardCharsets.UTF_8);
    }

    public void setVisitorId(byte[] visitorId) {
        this.visitorId = visitorId;
    }


}