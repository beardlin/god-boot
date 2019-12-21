package net.lantrack.module.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.flow.entity.ActFlowInstenceEntity;
import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;
import java.util.Map;

/**
 *@Description 节点审批记录
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
public interface ActNodeRecordService extends IService<ActNodeRecordEntity> {

    PageEntity queryPage(Map<String, Object> params);

    List<ActNodeRecordEntity> queryAllByFlowInstanceId(Integer flowInstanceId);

    ActNodeRecordEntity queryCurrentNodeRecordByFlowInstanceId(Integer flowInstanceId);

    MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> queryNextFlowNode(ActFlowInstenceEntity actFlowInstenceEntity, ActNodeRecordEntity currentActNodeRecordEntity);

    MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> queryFirstNodeRecord(ActFlowInstenceEntity actFlowInstenceEntity);
}

