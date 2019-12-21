package net.lantrack.module.flow.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 流程定义表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Data
@TableName("act_flow")
public class ActFlowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 流程名称
	 */
	private String flowName;
	/**
	 * 编码规则
	 */
	private String flowCode;
	/**
	 * 流程分类（字典）
	 */
	private Integer flowType;
	/**
	 * 流程分类名称
	 */
	private String flowTypeName;
	/**
	 * 流程描述
	 */
	private String flowDesc;
	/**
	 * 流程类型（0自由1固定节点）
	 */
	private Integer flowNodeType;
	/**
	 * 流程类型名称
	 */
	private String flowNodeTypeName;
	/**
	 * 流程表单类型(0固定表单默认1非关系型表单)
	 */
	private Integer formType;
	/**
	 * 流程可见范围（0全体1指定人可用）（当为1时flow_user表后期维护）
	 */
	private Integer flowScope;
	/**
	 * 自定义表单（当formType为1时使用  类型text）
	 */
	private String fromJson;
	/**
	 * 业务表单名称(当formType为0时使用）
	 */
	private String tableName;
	/**
	 * 路由地址(针对固定表单)
	 */
	private String routerUrl;
	/**
	 * 是否发布（0未发布1发布）
	 */
	private Integer flowStatus;
	/**
	 * 发布时间
	 */
	private Date publishDate;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
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
