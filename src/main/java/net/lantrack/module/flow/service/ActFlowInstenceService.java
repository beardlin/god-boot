package net.lantrack.module.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.flow.entity.ActFlowInstenceEntity;
import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import net.lantrack.module.flow.enums.FlowTableNameEnum;
import net.lantrack.module.flow.model.BaseFlowModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 *@Description 流程实例表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
public interface ActFlowInstenceService extends IService<ActFlowInstenceEntity> {

    PageEntity queryPage(Map<String, Object> params);


    // 0:我的申请，1:待办，2:已办
    PageEntity queryPage(int applyType, FlowTableNameEnum tableNameEnum, Map<String, Object> params);

    Integer saveInstanceByTable(BaseFlowModel entity);

    Boolean publish(Integer flowInstanceId);


    @Transactional(rollbackFor = Exception.class)
    Boolean audit(ActNodeRecordEntity entity);

    BaseFlowModel getAuditResultByInstanceId(BaseFlowModel baseFlowModel);

}

