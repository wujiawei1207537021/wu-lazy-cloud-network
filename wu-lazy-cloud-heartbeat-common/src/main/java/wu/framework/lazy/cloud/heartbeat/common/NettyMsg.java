package wu.framework.lazy.cloud.heartbeat.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Setter
@Getter
public class NettyMsg {
    // body 长度 type 1  clientId 4 data 4
    public static final int bodyLength = 9;

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
     * 消息传输数据
     * byte[] 长度 4
     */
    private byte[] data;


    @Override
    public String toString() {
        return "NettyProxyMsg [type=" + type + ", data=" + Arrays.toString(data) + ",clientId=" + Arrays.toString(clientId) + "]";
    }

}