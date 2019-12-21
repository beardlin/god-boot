
package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import net.lantrack.framework.common.validator.group.AddGroup;
import net.lantrack.framework.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *@Description  系统用户
 *@Author dahuzi
 *@Date 2019/10/29  17:47
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;

	/**
	 * 真实姓名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String realName;

	/**
	 * 用户名
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 密码
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;

	/**
	 * 盐
	 */
	private String salt;

	/**
	 * 邮箱
	 */
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 性别（0女1男）
	 */
	private Integer sex;
	/**
	 * 生日
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * QQ号
	 */
	private String qqNum;
	/**
	 * 微信号
	 */
	private String weiChart;

	/**
	 * 状态  0：禁用 1：正常  2：锁定
	 */
	private Integer status=1;

	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	private List<Long> roleIdList;

	@TableField(exist=false)
	private String roleIds;

	/**
	 * 创建者ID
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 工号
	 */
	private String userCode;
	/**
	 * 职务id
	 */
	private String dutyIds;
	/**
	 * 职务名称
	 */
	private String dutyNames;
	/**
	 * 所属机构id
	 */
	private Long officeId;
	/**
	 * 所属机构
	 */
	private String officeName;
	/**
	 * 登录失败次数
	 */
	private Integer errNum;
	/**
	 * 自动解锁时间
	 */
	private Date unlockTime;


}
