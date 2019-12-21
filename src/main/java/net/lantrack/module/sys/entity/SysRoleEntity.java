
package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *@Description  角色
 *@Author dahuzi
 *@Date 2019/10/29  17:47
 */
@Data
@TableName("sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色ID
	 */
	@TableId
	private Long roleId;

	/**
	 * 角色分类Id
	 */
	private Integer roleTypeId;

	/**
	 * 权限标识符集合
	 */
	private String permission;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String roleName;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建者ID
	 */
	private Long createUserId;



	@TableField(exist=false)
	private List<Long> menuIdList;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 是否拷贝,默认false
	 */
	@TableField(exist=false)
	private Boolean copy = false;
	/**
	 * 拷贝ID
	 */
	@TableField(exist=false)
	private Long copyId;

}
