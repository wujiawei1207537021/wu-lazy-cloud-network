package wu.framework.middleground.cloud.heartbeat.common.constant;

import io.netty.util.AttributeKey;

/**
 * netty 通道属性 key常量
 */
public class NettyChannelAttributeKey {
    public static final AttributeKey<String> CLIENT_ID_KEY = AttributeKey.newInstance("client_id");
}
