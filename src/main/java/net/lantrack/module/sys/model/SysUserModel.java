
package net.lantrack.module.sys.model;

import lombok.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *@Description  系统用户
 *@Author dahuzi
 *@Date 2019/10/29  17:47
 */
@Data
public class SysUserModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 真实姓名
	 */
	private String realName="";
	/**
	 * 用户名
	 */
	private String username="";
	/**
	 * 邮箱
	 */
	private String email="";

	/**
	 * 手机号
	 */
	private String mobile="";
	/**
	 * 性别（0女1男）
	 */
	private Integer sex;

	/**
	 * 生日
	 */
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
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 工号
	 */
	private String userCode="";
	/**
	 * 职务id
	 */
	private String dutyIds="";
	/**
	 * 职务名称
	 */
	private String dutyNames="";
	/**
	 * 所属机构id
	 */
	private Long officeId;
	/**
	 * 所属机构
	 */
	private String officeName="";
	/**
	 * 角色id
	 */
	private List<Long> roleIdList = new ArrayList<>();

}
