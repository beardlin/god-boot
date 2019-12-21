package net.lantrack.module.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.flow.entity.ActFlowNodeEntity;

import java.util.List;
import java.util.Map;

/**
 *@Description 审批节点表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
public interface ActFlowNodeService extends IService<ActFlowNodeEntity> {

    PageEntity queryPage(Map<String, Object> params);

    List<ActFlowNodeEntity> queryAllByFlowId(Integer flowId);

    /**
     * 校验相关的职务是否有人员，没有任何人员则返回fasle,有人员返回true
     * @param actFlowNode
     * @return
     */
    Boolean checkDuty(ActFlowNodeEntity actFlowNode);
}

