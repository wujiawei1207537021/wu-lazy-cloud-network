package wu.framework.lazy.cloud.heartbeat.server.netty.flow;

import io.netty.channel.Channel;
import org.springframework.util.ObjectUtils;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.AbstractHandleChannelFlowAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.ChannelFlow;
import wu.framework.lazy.cloud.heartbeat.common.enums.ChannelFlowEnum;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlow;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorFlowRepository;

/**
 * 出口流量处理
 */
public class ServerHandlerOutFlowHandler extends AbstractHandleChannelFlowAdvanced {
    private final VisitorFlowRepository visitorFlowRepository;

    public ServerHandlerOutFlowHandler(VisitorFlowRepository visitorFlowRepository) {
        this.visitorFlowRepository = visitorFlowRepository;
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
        VisitorFlow visitorFlow = new VisitorFlow();
        visitorFlow.setOutFlow(flow);
        visitorFlow.setClientId(clientId);
        visitorFlow.setVisitorPort(port);
        visitorFlow.setIsDeleted(false);
        // 查询是否存在已有流量 而后进行统计汇总
        VisitorFlow findOneVisitorFlowQuery = new VisitorFlow();
        findOneVisitorFlowQuery.setClientId(clientId);
        findOneVisitorFlowQuery.setVisitorPort(port);
        visitorFlowRepository.findOne(findOneVisitorFlowQuery).accept(existVisitorFlow -> {
            Integer outFlow = existVisitorFlow.getOutFlow();
            Integer inFlow = existVisitorFlow.getInFlow();
            if (!ObjectUtils.isEmpty(outFlow)) {
                visitorFlow.setOutFlow(visitorFlow.getOutFlow() + outFlow);
            }
            if (!ObjectUtils.isEmpty(inFlow)) {
                visitorFlow.setInFlow( inFlow);
            }
        });
        visitorFlowRepository.story(visitorFlow);
    }
}
