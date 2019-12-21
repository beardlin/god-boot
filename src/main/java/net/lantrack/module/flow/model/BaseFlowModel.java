package net.lantrack.module.flow.model;

import com.baomidou.mybatisplus.annotation.TableField;
import net.lantrack.module.flow.enums.FlowInstanceStatusEnum;
import net.lantrack.module.flow.enums.FlowTableNameEnum;
import lombok.Data;

import java.util.Date;

@Data
public class BaseFlowModel {
    private Integer flowInstanceId;
    /**
     * 流程状态（0草稿1审批中2结束3撤回）
     */
    @TableField(exist = false)
    private Integer flowStatus;
    @TableField(exist = false)
    private FlowTableNameEnum flowTableNameEnum;
    @TableField(exist = false)
    private String flowStatusMsg;
    /**
     * 申请人员
     */
    private String applyBy;
    /**
     * 申请人员名称
     */
    private String applyByName;
    /**
     * 申请时间
     */
    private Date applyDate;
    /**
     * 申请部门
     */
    private String applyDep;
    /**
     * 申请部门名称
     */
    private String applyDepName;

    public BaseFlowModel() {
    }
    public BaseFlowModel(Integer flowInstanceId) {
        this.flowInstanceId = flowInstanceId;
    }


}
