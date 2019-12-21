package net.lantrack.module.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.flow.dao.ActFlowInstenceDao;
import net.lantrack.module.flow.entity.ActFlowEntity;
import net.lantrack.module.flow.entity.ActFlowInstenceEntity;
import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import net.lantrack.module.flow.enums.*;
import net.lantrack.module.flow.model.BaseFlowModel;
import net.lantrack.module.flow.service.ActFlowInstenceService;
import net.lantrack.module.flow.service.ActFlowNodeService;
import net.lantrack.module.flow.service.ActFlowService;
import net.lantrack.module.flow.service.ActNodeRecordService;
import net.lantrack.module.flow.utils.CodeRuleUtils;
import net.lantrack.module.sys.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @ Description 流程实例表
 * @ Author ldahuzi
 * @ Date 2019-11-05 22:17:05
 */
@Service("actFlowInstenceService")
public class ActFlowInstenceServiceImpl extends ServiceImpl<ActFlowInstenceDao, ActFlowInstenceEntity> implements ActFlowInstenceService {

    @Autowired
    ActFlowService actFlowService;
    @Autowired
    ActFlowNodeService actFlowNodeService;
    @Autowired
    ActNodeRecordService actNodeRecordService;
    @Autowired
    SysUserService sysUserService;
    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<ActFlowInstenceEntity> page = this.page(
                new Query<ActFlowInstenceEntity>().getPage(params),
                new QueryWrapper<ActFlowInstenceEntity>()
        );

        return new PageEntity(page);
    }
    // 0:我的申请，1:待办，2:已办
    @Override
    public PageEntity queryPage(int applyType, FlowTableNameEnum tableNameEnum, Map<String, Object> params) {
        QueryWrapper<ActFlowInstenceEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("table_name",tableNameEnum.getTableName());
        IPage<ActFlowInstenceEntity> page = null;
        if (applyType== 0 ){
            queryWrapper.eq("create_by",UserUtils.getUserId()).orderByDesc("id");
            page = this.page(
                    new Query<ActFlowInstenceEntity>().getPage(params),
                    queryWrapper
            );
        }else if(applyType==1){
            //TODO 解析职务关系
            params.put("tableName",tableNameEnum.getTableName());
            params.put("userId",UserUtils.getUserId());
            params.put("approverOffice",UserUtils.getUserEntity().getOfficeId());
            String dutyIds = UserUtils.getUserEntity().getDutyIds();
            if(StringUtils.isNotBlank(dutyIds)){
                params.put("dutyIds", Arrays.asList( dutyIds.split(",")));
            }else{
                params.put("dutyIds",Arrays.asList(0));
            }

            page =  this.getBaseMapper().selectTodoFlowPage(new Query<Map<String, Object>>().getPage(params, "i.id", false) ,params);
        }else if(applyType==2){
            params.put("tableName",tableNameEnum.getTableName());
            params.put("userId",UserUtils.getUserId());
            page =  this.getBaseMapper().selectHistoryFlowPage(new Query<Map<String, Object>>().getPage(params, "i.id", false) ,params);
        }
        return new PageEntity(page);
    }

    @Override
    public Integer saveInstanceByTable(BaseFlowModel entity) {
        if (entity.getFlowTableNameEnum()==null){
            return null;
        }
        ActFlowEntity actFlowEntity = actFlowService.getOneByTableName(entity.getFlowTableNameEnum().getTableName());
        ActFlowInstenceEntity actFlowInstenceEntity = new ActFlowInstenceEntity();
        actFlowInstenceEntity.setFlowId(actFlowEntity.getId());
        String flowCode = actFlowEntity.getFlowCode();
        actFlowInstenceEntity.setCodeRule(CodeRuleUtils.getCodeRuleStrByCodeRule(flowCode));
        //TODO integet 需要按照日期重新计算吗？
        Integer integer = this.getBaseMapper().selectMaxCodeAotoByFlowId(actFlowEntity.getId());
        actFlowInstenceEntity.setCodeAoto(integer == null ? 1 : ++integer);
        //TODO   actFlowInstenceEntity.setDataId(baseFlowReq.getDataId());
        actFlowInstenceEntity.setTableName(entity.getFlowTableNameEnum().getTableName());
        actFlowInstenceEntity.setFlowStatus(FlowInstanceStatusEnum.DRAFT.getCode());
        actFlowInstenceEntity.setApproverType(ApproverTypeEnum.PERSONNEL.getCode());
        // TODO 发起人的id
        actFlowInstenceEntity.setApproverId(UserUtils.getUserId().toString());
        actFlowInstenceEntity.setApproverType(ApproverTypeEnum.PERSONNEL.getCode());
        actFlowInstenceEntity.setApproverOffice(UserUtils.getUserEntity().getOfficeId());
        actFlowInstenceEntity.setCreateBy(UserUtils.getUserId().toString());
        actFlowInstenceEntity.setCreateDate(new Date());
        actFlowInstenceEntity.setFlowCode(actFlowInstenceEntity.getCodeRule()+ String.format("%06d",
                actFlowInstenceEntity.getCodeAoto()));
        this.save(actFlowInstenceEntity);
        MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> actNodeRecordEntityActFlowNodeEntityMutablePair = actNodeRecordService.queryFirstNodeRecord(actFlowInstenceEntity);
        ActNodeRecordEntity nextRecord = actNodeRecordEntityActFlowNodeEntityMutablePair.getLeft();
        actNodeRecordService.save(nextRecord);
        this.setApplyData(entity,actFlowInstenceEntity.getId());
        return actFlowInstenceEntity.getId();
    }

    /**
     * 可以发布信息,自定义流程先不考虑
     *
     * @param flowInstanceId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean publish(Integer flowInstanceId) {
        // TODO 权限校验
        ActFlowInstenceEntity actFlowInstenceEntity = this.getById(flowInstanceId);
        // 检验状体是否正确
        if (actFlowInstenceEntity ==
                null || !FlowInstanceStatusEnum.DRAFT.getCode().equals(actFlowInstenceEntity.getFlowStatus())) {
            return false;
        }
        //流程状态改变
        actFlowInstenceEntity.setFlowStatus(FlowInstanceStatusEnum.PROCESSED.getCode());
        // 当前节点
        ActNodeRecordEntity currentActNodeRecordEntity =
                actNodeRecordService.queryCurrentNodeRecordByFlowInstanceId(flowInstanceId);
        //TODO 审批人信息的获取
        currentActNodeRecordEntity.setApproverId(UserUtils.getUserId().toString());
        currentActNodeRecordEntity.setApproverName(UserUtils.getUserName());
        currentActNodeRecordEntity.setApproveResult(ApproveResultEnum.PASS.getCode());
        currentActNodeRecordEntity.setApproveComments(ApproveResultEnum.PASS.getMsg());
        currentActNodeRecordEntity.setApproveDate(new Date());
        actNodeRecordService.updateById(currentActNodeRecordEntity);

        MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> actNodeRecordEntityActFlowNodeEntityMutablePair =
                actNodeRecordService.queryNextFlowNode(actFlowInstenceEntity, currentActNodeRecordEntity);
        ActNodeRecordEntity nextRecord = actNodeRecordEntityActFlowNodeEntityMutablePair.getLeft();
        ActFlowNodeEntity right = actNodeRecordEntityActFlowNodeEntityMutablePair.getRight();

        // TODO 核实审批人信息与发布人员是否为同一个人
        dealApproverInfo(actFlowInstenceEntity,right);
        actNodeRecordService.save(nextRecord);
        actFlowInstenceEntity.setNodeRecordId(nextRecord.getId().longValue());
        this.updateById(actFlowInstenceEntity);
        // 设置下一个节点的审批人
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean audit(ActNodeRecordEntity entity) {

        Integer approveResult = entity.getApproveResult();
        ApproveResultEnum approveResultEnum = ApproveResultEnum.valueOf(approveResult);

        if (approveResultEnum == null || ApproveResultEnum.PENDING.equals(approveResultEnum)) {
            return false;
        }
        String approveComments = entity.getApproveComments();
        // TODO 权限校验
        ActFlowInstenceEntity actFlowInstenceEntity = this.getById(entity.getFlowInstanceId());
        ActNodeRecordEntity currentActNodeRecordEntity = actNodeRecordService.getById(actFlowInstenceEntity.getNodeRecordId());
        if (currentActNodeRecordEntity == null ||
                !ApproveResultEnum.PENDING.getCode().equals(currentActNodeRecordEntity.getApproveResult())
        ) {
            return false;
        }
        if (actFlowInstenceEntity ==
                null || !FlowInstanceStatusEnum.PROCESSED.getCode().equals(actFlowInstenceEntity.getFlowStatus())) {
            return false;
        }
        //当前节点内容的变更开始
        currentActNodeRecordEntity.setApproverId(UserUtils.getUserId().toString());
        currentActNodeRecordEntity.setApproverName(UserUtils.getUserEntity().getRealName());
        currentActNodeRecordEntity.setApproveResult(approveResult);
        currentActNodeRecordEntity.setApproveComments(approveComments);
        currentActNodeRecordEntity.setApproveDate(new Date());
        actNodeRecordService.updateById(currentActNodeRecordEntity);
        // 当前节点内容的变更结束

        // 总流程的状态变更开始
        if (ApproveResultEnum.STOP.equals(approveResultEnum)) {
            actFlowInstenceEntity.setFlowStatus(FlowInstanceStatusEnum.END.getCode());
            this.updateById(actFlowInstenceEntity);
            return true;
        }
        // 下一个节点内容的获取开始
        MutablePair<ActNodeRecordEntity, ActFlowNodeEntity> nextFlowNode =
                actNodeRecordService.queryNextFlowNode(actFlowInstenceEntity, currentActNodeRecordEntity);
        ActNodeRecordEntity nextRecord = nextFlowNode.getLeft();
        actNodeRecordService.save(nextRecord);
        actFlowInstenceEntity.setNodeRecordId(nextRecord.getId().longValue());
        // 下一个节点内容的获取结束

        // 总流程的状态变更开始
        ActFlowNodeEntity right = nextFlowNode.getRight();
        FlowNodeTypeEnum flowNodeTypeEnum = FlowNodeTypeEnum.valueOf(right.getNodeType());
        if (FlowNodeTypeEnum.END.equals(flowNodeTypeEnum)) {
            actFlowInstenceEntity.setFlowStatus(FlowInstanceStatusEnum.END.getCode());
        } else if (FlowNodeTypeEnum.START.equals(flowNodeTypeEnum)) {
            actFlowInstenceEntity.setFlowStatus(FlowInstanceStatusEnum.DRAFT.getCode());
            //退回给创建人员
            actFlowInstenceEntity.setApproverType(ApproverTypeEnum.PERSONNEL.getCode());
            actFlowInstenceEntity.setApproverId(actFlowInstenceEntity.getCreateBy());
        } else {
            dealApproverInfo(actFlowInstenceEntity,right);
        }
        this.updateById(actFlowInstenceEntity);
        return true;
    }

    private ActFlowInstenceEntity dealApproverInfo(ActFlowInstenceEntity actFlowInstenceEntity ,
                                                  ActFlowNodeEntity right ){
        actFlowInstenceEntity.setApproverType(right.getApproverType());
        actFlowInstenceEntity.setApproverId(right.getApproverId());
        actFlowInstenceEntity.setApproverOffice(right.getApproverOffice());
        if (right.getApproverType()==2){
            if (right.getCrosOffcie().equals(0)){
                Integer integer = sysUserService.queryDutyCount(UserUtils.getUserEntity().getOfficeId(), right.getApproverId());
                if (integer>0) {
                    actFlowInstenceEntity.setApproverOffice(UserUtils.getUserEntity().getOfficeId());
                }else {
                    actFlowInstenceEntity.setApproverType(ApproverTypeEnum.PERSONNEL.getCode());
                    actFlowInstenceEntity.setApproverId(sysUserService.queryMasterId(UserUtils.getUserEntity().getOfficeId()));
                }
            }
        }

        return actFlowInstenceEntity;
    }
    @Override
    public BaseFlowModel getAuditResultByInstanceId(BaseFlowModel baseFlowModel) {
        ActFlowInstenceEntity byId = this.getById(baseFlowModel.getFlowInstanceId());
        if (byId!=null){
            baseFlowModel.setFlowStatus(byId.getFlowStatus());
            FlowInstanceStatusEnum flowInstanceStatusEnum = FlowInstanceStatusEnum.valueOf(byId.getFlowStatus());
            baseFlowModel.setFlowStatusMsg(flowInstanceStatusEnum == null ? "" : flowInstanceStatusEnum.getMsg());
        }
        return baseFlowModel;
    }

    public BaseFlowModel setApplyData(BaseFlowModel entity,Integer flowInstanceId) {
        entity.setFlowInstanceId(flowInstanceId);
        if (StringUtils.isBlank(entity.getApplyBy())){
            entity.setApplyBy(UserUtils.getUserId().toString());
        }
        if (StringUtils.isBlank(entity.getApplyByName())){
            entity.setApplyByName(UserUtils.getUserName());
        }
        if (StringUtils.isBlank(entity.getApplyDep())){
            entity.setApplyDep(UserUtils.getUserEntity().getOfficeId() == null ? "" : UserUtils.getUserEntity().getOfficeId().toString());

        }
        if (StringUtils.isBlank(entity.getApplyDepName())){
            entity.setApplyDepName(UserUtils.getUserEntity().getOfficeName());
        }
        entity.setApplyDate(new Date());
        return entity;
    }
}
