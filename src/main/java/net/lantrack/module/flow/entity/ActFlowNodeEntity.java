package net.lantrack.module.flow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 审批节点表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Data
@TableName("act_flow_node")
public class ActFlowNodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 所属流程id
	 */
	private Integer flowId;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 审批人类型（1人员2职务）,如果当前部门下没有该角色人员则默认部门负责人审批
	 */
	private Integer approverType;
	/**
	 * 当前节点审批人id(多个用逗号“,”分割)
	 */
	private String approverId;
	/**
	 * 0本部门1跨部门（当approver_id为职务类型时才启用）
	 */
	private Integer crosOffcie;
	/**
	 * 待审批部门id
	 */
	private Long approverOffice;
	/**
	 * 多级审批节点排序
	 */
	private Integer nodeSort;
	/**
	 * 节点类型(0普通1或签2并签,9结束，8开始)（0普通只可选择一个审批人 或签和并签可选择多个审批人）
	 */
	private Integer nodeType;
	/**
	 * 是否可以终止流程（0不显示1显示）
	 */
	private Integer endFlow;
	/**
	 * 退回节点（0发起人1上一级节点2之前任意节点）
	 */
	private Integer returnTo;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 修改时间
	 */
	private Date updateDate;
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
