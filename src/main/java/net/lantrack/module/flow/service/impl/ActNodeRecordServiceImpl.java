package net.lantrack.module.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.module.flow.dao.ActNodeRecordDao;
import net.lantrack.module.flow.entity.ActFlowInstenceEntity;
import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import net.lantrack.module.flow.enums.ApproveResultEnum;
import net.lantrack.module.flow.enums.FlowNodeTypeEnum;
import net.lantrack.module.flow.enums.ReturnToEnum;
import net.lantrack.module.flow.service.ActFlowNodeService;
import net.lantrack.module.flow.service.ActNodeRecordService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 节点审批记录
 * @Author ldahuzi
 * @Date 2019-11-05 22:17:05
 */
@Service("actNodeRecordService")
public class ActNodeRecordServiceImpl extends ServiceImpl<ActNodeRecordDao, ActNodeRecordEntity> implements ActNodeRecordService {
    @Autowired
    ActFlowNodeService actFlowNodeService;

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<ActNodeRecordEntity> page = this.page(
                new Query<ActNodeRecordEntity>().getPage(params),
                new QueryWrapper<ActNodeRecordEntity>()
        );

        return new PageEntity(page);
    }


    @Override
    public List<ActNodeRecordEntity> queryAllByFlowInstanceId(Integer flowInstanceId) {
        List<ActNodeRecordEntity> list = this.list(new QueryWrapper<ActNodeRecordEntity>().eq("flow_instance_id",
                flowInstanceId).orderByDesc("id"));
        return list;
    }
    @Override
    public ActNodeRecordEntity queryCurrentNodeRecordByFlowInstanceId(Integer flowInstanceId) {
        List<ActNodeRecordEntity> list = this.list(new QueryWrapper<ActNodeRecordEntity>().eq("flow_instance_id",
                flowInstanceId).orderByDesc("id").last("LIMIT 1"));
        if (CollectionUtils.isNotEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> queryNextFlowNode(ActFlowInstenceEntity actFlowInstenceEntity, ActNodeRecordEntity currentActNodeRecordEntity) {
        ActNodeRecordEntity newNode = null;
        List<ActFlowNodeEntity> actFlowNodeEntities = actFlowNodeService.queryAllByFlowId(actFlowInstenceEntity.getFlowId());
        ActFlowNodeEntity nextActFlowNodeEntity = null;
        if (CollectionUtils.isEmpty(actFlowNodeEntities)) {
            return MutablePair.of(newNode, nextActFlowNodeEntity);
        }

        Integer currentNodeId = currentActNodeRecordEntity.getNodeId();
        ApproveResultEnum of = ApproveResultEnum.valueOf(currentActNodeRecordEntity.getApproveResult());
        if (of == null) {
            return MutablePair.of(newNode, nextActFlowNodeEntity);
        }
        for (int i = 0; i < actFlowNodeEntities.size(); i++) {
            ActFlowNodeEntity flowNodeEntity = actFlowNodeEntities.get(i);
            if (!flowNodeEntity.getId().equals(currentNodeId)) {
                continue;
            }

            switch (of) {
                case PASS:
                    if (i + 1 < actFlowNodeEntities.size()) {
                        nextActFlowNodeEntity = actFlowNodeEntities.get(i + 1);
                    }else {
                        // 需要结束了
                    }
                    break;
                case NO_PASS:
                    Integer returnTo = flowNodeEntity.getReturnTo();
                    ReturnToEnum.valueOf(returnTo);


                    if (i - 1 >= 0) {
                        nextActFlowNodeEntity = actFlowNodeEntities.get(i - 1);
                        break;
                    }else {
                        // 需要变更成草稿,
                    }
                    break;
                case STOP:
                    // TODO 校验该节点是否允许终止
                    //结束流程
                    break;
                default:
                    break;
            }
            break;
        }

        newNode = this.createNewNode(actFlowInstenceEntity, nextActFlowNodeEntity);
        return MutablePair.of(newNode, nextActFlowNodeEntity);
    }

    /**
     * @return
     * @method
     * @description 查询第一个审批节点，并且创建相关的审批记录，针对固定流程节点,通常情况是开始节点 节点id 为 8
     * @date: 2019/11/18 21:39
     * @throw
     */
    @Override
    public MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> queryFirstNodeRecord(ActFlowInstenceEntity actFlowInstenceEntity) {
        List<ActFlowNodeEntity> actFlowNodeEntities = actFlowNodeService.queryAllByFlowId(actFlowInstenceEntity.getFlowId());
        ActFlowNodeEntity actFlowNodeEntity = null;
        if (CollectionUtils.isEmpty(actFlowNodeEntities)) {
            return null;
        }
        actFlowNodeEntity = actFlowNodeEntities.get(0);
        ActNodeRecordEntity newNode = this.createNewNode(actFlowInstenceEntity, actFlowNodeEntity);
        return MutablePair.of(newNode, actFlowNodeEntity);
    }


    public ActNodeRecordEntity createNewNode(ActFlowInstenceEntity actFlowInstenceEntity,
                                             ActFlowNodeEntity actFlowNodeEntity) {
        if (actFlowNodeEntity == null) {
            return null;
        }
        ActNodeRecordEntity entity = new ActNodeRecordEntity();
        entity.setFlowId(actFlowInstenceEntity.getFlowId());
        entity.setFlowInstanceId(actFlowInstenceEntity.getId());
        entity.setNodeId(actFlowNodeEntity.getId());
        entity.setNodeName(actFlowNodeEntity.getNodeName());
        entity.setNodeType(actFlowNodeEntity.getNodeType());
        FlowNodeTypeEnum flowNodeTypeEnum = FlowNodeTypeEnum.valueOf(actFlowNodeEntity.getNodeType());
        if (FlowNodeTypeEnum.END.equals(flowNodeTypeEnum)){
            entity.setApproveResult(ApproveResultEnum.PASS.getCode());
        }else {
            entity.setApproveResult(ApproveResultEnum.PENDING.getCode());
        }
        entity.setCreateDate(new Date());
        return entity;
    }
}
