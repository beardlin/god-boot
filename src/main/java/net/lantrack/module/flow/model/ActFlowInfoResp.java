package net.lantrack.module.flow.model;

import net.lantrack.module.flow.entity.ActFlowEntity;
import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import lombok.Data;

import java.util.List;

@Data
public class ActFlowInfoResp {

    private ActFlowEntity flow;
    private List<ActFlowNodeEntity> nodes;
}
