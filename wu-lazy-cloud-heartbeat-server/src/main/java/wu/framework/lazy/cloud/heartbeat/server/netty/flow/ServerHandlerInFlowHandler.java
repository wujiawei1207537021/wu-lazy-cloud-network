package wu.framework.lazy.cloud.heartbeat.server.netty.flow;

import io.netty.channel.Channel;
import org.springframework.util.ObjectUtils;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.AbstractHandleChannelFlowAdvanced;
import wu.framework.lazy.cloud.heartbeat.common.advanced.flow.ChannelFlow;
import wu.framework.lazy.cloud.heartbeat.common.enums.ChannelFlowEnum;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlowRepository;

/**
 * 进口流量处理
 */
public class ServerHandlerInFlowHandler extends AbstractHandleChannelFlowAdvanced {
    private final VisitorPortFlowRepository visitorPortFlowRepository;

    public ServerHandlerInFlowHandler(VisitorPortFlowRepository visitorPortFlowRepository) {
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
        return ChannelFlowEnum.IN_FLOW.equals(channelFlow.channelFlowEnum());
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
        visitorPortFlow.setInFlow(flow);
        visitorPortFlow.setClientId(clientId);
        visitorPortFlow.setVisitorPort(port);
        visitorPortFlow.setIsDeleted(false);
        // 查询是否存在已有流量 而后进行统计汇总
        VisitorPortFlow findOneVisitorPortFlowQuery = new VisitorPortFlow();
        findOneVisitorPortFlowQuery.setClientId(clientId);
        findOneVisitorPortFlowQuery.setVisitorPort(port);
        visitorPortFlowRepository.findOne(findOneVisitorPortFlowQuery).accept(existVisitorFlow -> {
            Integer inFlow = existVisitorFlow.getInFlow();
            Integer outFlow = existVisitorFlow.getOutFlow();
            if(!ObjectUtils.isEmpty(inFlow)){
                visitorPortFlow.setInFlow(visitorPortFlow.getInFlow()+inFlow);
            }
            if (!ObjectUtils.isEmpty(outFlow)) {
                visitorPortFlow.setOutFlow( outFlow);
            }
        });
        visitorPortFlowRepository.story(visitorPortFlow);
    }
}
