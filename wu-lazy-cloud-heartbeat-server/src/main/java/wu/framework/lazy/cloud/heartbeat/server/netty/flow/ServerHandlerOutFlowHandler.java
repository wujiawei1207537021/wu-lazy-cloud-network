package wu.framework.lazy.cloud.heartbeat.server.netty.flow;

import io.netty.channel.Channel;
import org.springframework.util.ObjectUtils;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.AbstractHandleChannelFlowAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.ChannelFlow;
import wu.framework.lazy.cloud.heartbeat.common.enums.ChannelFlowEnum;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlowRepository;

/**
 * 出口流量处理
 */
public class ServerHandlerOutFlowHandler extends AbstractHandleChannelFlowAdvanced {
    private final VisitorPortFlowRepository visitorPortFlowRepository;

    public ServerHandlerOutFlowHandler(VisitorPortFlowRepository visitorPortFlowRepository) {
        this.visitorPortFlowRepository = visitorPortFlowRepository;
    }

    /**
     * 处理是否支持这种类型
     *
     * @param channelFlow 数据
     * @return boolean
     */
    @Override
    protected boolean doSupport(ChannelFlow channelFlow) {
        return ChannelFlowEnum.OUT_FLOW.equals(channelFlow.channelFlowEnum());
    }

    /**
     * 处理当前数据
     *
     * @param channel     当前通道
     * @param channelFlow 通道数据
     */
    @Override
    protected void doHandler(Channel channel, ChannelFlow channelFlow) {
        String clientId = channelFlow.clientId();
        Integer port = channelFlow.port();
        Integer flow = channelFlow.flow();

        // 进口流量处理
        VisitorPortFlow visitorPortFlow = new VisitorPortFlow();
        visitorPortFlow.setOutFlow(flow);
        visitorPortFlow.setClientId(clientId);
        visitorPortFlow.setVisitorPort(port);
        visitorPortFlow.setIsDeleted(false);
        // 查询是否存在已有流量 而后进行统计汇总
        VisitorPortFlow findOneVisitorPortFlowQuery = new VisitorPortFlow();
        findOneVisitorPortFlowQuery.setClientId(clientId);
        findOneVisitorPortFlowQuery.setVisitorPort(port);
        visitorPortFlowRepository.findOne(findOneVisitorPortFlowQuery).accept(existVisitorFlow -> {
            Integer outFlow = existVisitorFlow.getOutFlow();
            Integer inFlow = existVisitorFlow.getInFlow();
            if (!ObjectUtils.isEmpty(outFlow)) {
                visitorPortFlow.setOutFlow(visitorPortFlow.getOutFlow() + outFlow);
            }
            if (!ObjectUtils.isEmpty(inFlow)) {
                visitorPortFlow.setInFlow(inFlow);
            }
        });
        visitorPortFlowRepository.story(visitorPortFlow);
    }
}
