package net.lantrack.module.flow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 节点审批记录
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Data
@TableName("act_node_record")
public class ActNodeRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据id
	 */
	@TableId
	private Integer id;
	/**
	 * 所属流程id
	 */
	private Integer flowId;
	/**
	 * 所属流程实例id
	 */
	private Integer flowInstanceId;
	/**
	 * 节点id
	 */
	private Integer nodeId;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点类型(0普通1或签2并签,9结束，8开始)（0普通只可选择一个审批人 或签和并签可选择多个审批人）
	 */
	private Integer nodeType;
	/**
	 * 审批人类型（1人员2职务）,如果当前部门下没有该角色人员则默认部门负责人审批
	 */
	private Integer approverType;
	/**
	 * 当前节点审批人id
	 */
	private String approverId;
	/**
	 * 待审批部门id
	 */
	private Long approverOffice;
	/**
	 * 当前节点审批人姓名
	 */
	private String approverName;
	/**
	 * 审批结果（0待处理1通过2不通过3终止）
	 */
	private Integer approveResult;
	/**
	 * 审批意见
	 */
	private String approveComments;
	/**
	 * 审批时间
	 */
	private Date approveDate;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 *
	 */
	private String stand1;
	/**
	 *
	 */
	private String stand2;
	/**
	 *
	 */
	private String stand3;
	/**
	 *
	 */
	private String stand4;
	/**
	 *
	 */
	private String stand5;
	/**
	 *
	 */
	private String stand6;

}
