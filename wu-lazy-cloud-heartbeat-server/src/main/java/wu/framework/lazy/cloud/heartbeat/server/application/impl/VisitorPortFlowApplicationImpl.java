package wu.framework.lazy.cloud.heartbeat.server.application.impl;

import com.wu.framework.database.lazy.web.plus.stereotype.LazyApplication;
import com.wu.framework.inner.lazy.database.expand.database.persistence.domain.LazyPage;
import com.wu.framework.response.Result;
import jakarta.annotation.Resource;
import wu.framework.lazy.cloud.heartbeat.server.application.VisitorPortFlowApplication;
import wu.framework.lazy.cloud.heartbeat.server.application.assembler.VisitorPortFlowDTOAssembler;
import wu.framework.lazy.cloud.heartbeat.server.application.command.visitor.flow.*;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorFlowDTO;
import wu.framework.lazy.cloud.heartbeat.server.application.dto.VisitorPortFlowDTO;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlow;
import wu.framework.lazy.cloud.heartbeat.server.domain.model.visitor.flow.VisitorPortFlowRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * describe 访客端流量
 *
 * @author Jia wei Wu
 * @date 2024/01/24 05:19 下午
 * @see com.wu.framework.inner.lazy.persistence.reverse.lazy.ddd.DefaultDDDLazyApplicationImpl
 **/
@LazyApplication
public class VisitorPortFlowApplicationImpl implements VisitorPortFlowApplication {

    @Resource
    VisitorPortFlowRepository visitorPortFlowRepository;

    /**
     * describe 新增访客端流量
     *
     * @param visitorPortFlowStoryCommand 新增访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量新增后领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlow> story(VisitorPortFlowStoryCommand visitorPortFlowStoryCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorPortFlowStoryCommand);
        return visitorPortFlowRepository.story(visitorPortFlow);
    }

    /**
     * describe 批量新增访客端流量
     *
     * @param visitorPortFlowStoryCommandList 批量新增访客端流量
     * @return {@link Result<List< VisitorPortFlow >>} 访客端流量新增后领域对象集合
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorPortFlow>> batchStory(List<VisitorPortFlowStoryCommand> visitorPortFlowStoryCommandList) {
        List<VisitorPortFlow> visitorPortFlowList = visitorPortFlowStoryCommandList.stream().map(VisitorPortFlowDTOAssembler.INSTANCE::toVisitorFlow).collect(Collectors.toList());
        return visitorPortFlowRepository.batchStory(visitorPortFlowList);
    }

    /**
     * describe 更新访客端流量
     *
     * @param visitorPortFlowUpdateCommand 更新访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量领域对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlow> updateOne(VisitorPortFlowUpdateCommand visitorPortFlowUpdateCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorPortFlowUpdateCommand);
        return visitorPortFlowRepository.story(visitorPortFlow);
    }

    /**
     * describe 查询单个访客端流量
     *
     * @param visitorPortFlowQueryOneCommand 查询单个访客端流量
     * @return {@link Result< VisitorFlowDTO >} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlowDTO> findOne(VisitorPortFlowQueryOneCommand visitorPortFlowQueryOneCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorPortFlowQueryOneCommand);
        return visitorPortFlowRepository.findOne(visitorPortFlow).convert(VisitorPortFlowDTOAssembler.INSTANCE::fromVisitorFlow);
    }

    /**
     * describe 查询多个访客端流量
     *
     * @param visitorFlowQueryListCommand 查询多个访客端流量
     * @return {@link Result<List< VisitorFlowDTO >>} 访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<List<VisitorPortFlowDTO>> findList(VisitorPortFlowQueryListCommand visitorFlowQueryListCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorFlowQueryListCommand);
        return visitorPortFlowRepository.findList(visitorPortFlow).convert(visitorFlows -> visitorFlows.stream().map(VisitorPortFlowDTOAssembler.INSTANCE::fromVisitorFlow).collect(Collectors.toList()));
    }

    /**
     * describe 分页查询多个访客端流量
     *
     * @param visitorPortFlowQueryListCommand 分页查询多个访客端流量
     * @return {@link Result<LazyPage< VisitorPortFlowDTO >>} 分页访客端流量DTO对象
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<LazyPage<VisitorPortFlowDTO>> findPage(int size, int current, VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorPortFlowQueryListCommand);
        return visitorPortFlowRepository.findPage(size, current, visitorPortFlow).convert(page -> page.convert(VisitorPortFlowDTOAssembler.INSTANCE::fromVisitorFlow));
    }

    /**
     * describe 删除访客端流量
     *
     * @param visitorPortFlowRemoveCommand 删除访客端流量
     * @return {@link Result< VisitorPortFlow >} 访客端流量
     * @author Jia wei Wu
     * @date 2024/01/24 05:19 下午
     **/

    @Override
    public Result<VisitorPortFlow> remove(VisitorPortFlowRemoveCommand visitorPortFlowRemoveCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorPortFlowRemoveCommand);
        return visitorPortFlowRepository.remove(visitorPortFlow);
    }

    /**
     * 根据客户端查询流量
     *
     * @param size                            分页大小
     * @param current                         分页
     * @param visitorPortFlowQueryListCommand 查询条件
     * @return {@link Result<LazyPage< VisitorFlowDTO >>} 分页访客端流量DTO对象
     */
    @Override
    public Result<LazyPage<VisitorFlowDTO>> findClientFlowPage(int size, int current, VisitorPortFlowQueryListCommand visitorPortFlowQueryListCommand) {
        VisitorPortFlow visitorPortFlow = VisitorPortFlowDTOAssembler.INSTANCE.toVisitorFlow(visitorPortFlowQueryListCommand);
        return visitorPortFlowRepository.findPageGroupByClientId(size, current, visitorPortFlow).convert(visitorPortFlowLazyPage -> {
            List<String> clientIdList = visitorPortFlowLazyPage.getRecord().stream().map(VisitorPortFlow::getClientId).toList();
            List<VisitorPortFlow> visitorPortFlowList = visitorPortFlowRepository.findListByClientIds(clientIdList);


            // 根据客户端进行分组

            List<VisitorPortFlowDTO> visitorPortFlowDTOList = visitorPortFlowList.stream().map(VisitorPortFlowDTOAssembler.INSTANCE::fromVisitorFlow).toList();


            Map<String/*客户端ID*/, List<VisitorPortFlowDTO>> client_port_flow_map = visitorPortFlowDTOList.stream().collect(Collectors.groupingBy(visitorPortFlowDTO -> visitorPortFlowDTO.getClientId()));

            Map<String/*客户端ID*/, Integer/*客户端进口流量*/> client_in_flow = visitorPortFlowDTOList.stream()
                    .collect(
                            Collectors.groupingBy(VisitorPortFlowDTO::getClientId,
                                    Collectors.summingInt(VisitorPortFlowDTO::getInFlowSize)));

            Map<String/*客户端ID*/, Integer/*客户端出口流量*/> client_out_flow = visitorPortFlowDTOList.stream()
                    .collect(
                            Collectors.groupingBy(VisitorPortFlowDTO::getClientId,
                                    Collectors.summingInt(VisitorPortFlowDTO::getOutFlowSize)));

            return visitorPortFlowLazyPage.convert(visitorPortFlow1 -> {
                String clientId = visitorPortFlow1.getClientId();
                VisitorFlowDTO visitorFlowDTO = new VisitorFlowDTO();
                visitorFlowDTO.setClientId(clientId);
                visitorFlowDTO.setInFlow(client_in_flow.getOrDefault(clientId, 0));
                visitorFlowDTO.setOutFlow(client_out_flow.getOrDefault(clientId, 0));
                visitorFlowDTO.setVisitorPortFlowDTOList(client_port_flow_map.getOrDefault(clientId, new ArrayList<>()));
                return visitorFlowDTO;
            });
        });
    }
}