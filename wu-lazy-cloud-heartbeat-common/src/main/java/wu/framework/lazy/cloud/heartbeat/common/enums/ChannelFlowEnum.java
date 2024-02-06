package wu.framework.lazy.cloud.heartbeat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通道流量类型
 */
@Getter
@AllArgsConstructor
public enum ChannelFlowEnum {

    // 出口流量
    OUT_FLOW,
    // 进口流量
    IN_FLOW
}
