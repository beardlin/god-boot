package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-19 20:50:07
 */
@Data
@TableName("sys_role_type")
public class SysRoleTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 角色分类名称
	 */
	private String typeName;
	/**
	 *
	 */
	private Integer typeSort;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 当前分类下的权限
	 */
	@TableField(exist = false)
	private List<SysRoleEntity> roles;

}
