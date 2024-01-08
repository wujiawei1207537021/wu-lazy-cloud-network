package wu.framework.middleground.cloud.heartbeat.common;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * describe 内网穿透映射 真实客户端
 *
 * @author Jia wei Wu
 * @date 2023/12/29 05:21 下午
 **/
@Data
@Accessors(chain = true)
public class InternalNetworkPenetrationRealClient {


    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端目标地址
     */
    private String clientTargetIp;

    /**
     * 客户端目标端口
     */
    private Integer clientTargetPort;


    /**
     * 访问端口
     */
    private Integer visitorPort;
    /**
     * 访客ID
     */
    private String visitorId;
}