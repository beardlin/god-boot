package net.lantrack.module.flow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 流程实例表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Data
@TableName("act_flow_instence")
public class ActFlowInstenceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 实例id
	 */
	@TableId
	private Integer id;
	/**
	 * 流程定义id
	 */
	private Integer flowId;
	/**
	 * 编码规则
	 */
	private String codeRule;
	/**
	 * 递增编号
	 */
	private Integer codeAoto;
	private String flowCode;
	/**
	 * 数据id
	 */
	private String dataId;
	/**
	 * 表单名称(针对固定表单)
	 */
	private String tableName;
	/**
	 * 自定义表单内容Json(针对非固定表单)
	 */
	private String tableJson;
	/**
	 * 流程状态（0草稿1审批中2结束3撤回）
	 */
	private Integer flowStatus;
	/**
	 * 审批人类型（1人员2职务）,如果当前部门下没有该角色人员则默认部门负责人审批
	 */
	private Integer approverType;
	/**
	 * 当前节点审批人id(多个用逗号“,”分割)
	 */
	private String approverId;
	/**
	 * 待审批部门id
	 */
	private Long approverOffice;
    /**
     * 审批记录id(快速定位)
     */
	private Long nodeRecordId;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 预留1
	 */
	private String stand1;
	/**
	 * 预留2
	 */
	private String stand2;
	/**
	 * 预留3
	 */
	private String stand3;
	/**
	 * 预留4
	 */
	private String stand4;
	/**
	 * 预留5
	 */
	private String stand5;
	/**
	 * 预留6
	 */
	private String stand6;

}
