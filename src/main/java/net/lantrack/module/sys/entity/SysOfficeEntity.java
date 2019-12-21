package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 组织机构表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Data
@TableName("sys_office")
public class SysOfficeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 树id
	 */
	@TableId
	private Long id;
	/**
	 * 父id
	 */
	private Long pId;
	/**
	 * 名称
	 */
	private String oName;
	/**
	 * 排序
	 */
	private Integer oSort;
	/**
	 * 类型（1集团2区域3公司4部门5小组）
	 */
	private Integer oType;
	/**
	 * 类型
	 */
	private String oTypeName;
	/**
	 * 负责人id
	 */
	private String oMasterId;
	/**
	 * 负责人
	 */
	private String oMasterName;
	/**
	 * 邮箱
	 */
	private String oEmail;
	/**
	 * 电话
	 */
	private String oPhone;
	/**
	 * 地址
	 */
	private String oAddress;
	/**
	 * 状态（0正常1停用）
	 */
	private Integer oStatus;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新人
	 */
	private String updateBy;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 删除标记（0否1是）
	 */
	private Integer delFlag;

}
