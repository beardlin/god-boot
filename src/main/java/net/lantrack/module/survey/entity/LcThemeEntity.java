package net.lantrack.module.survey.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 问卷模板
 *@Author ldahuzi
 *@Date 2019-12-12 10:16:41
 */
@Data
@TableName("lc_theme")
public class LcThemeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private String id;
	/**
	 * 标题
	 */
	private String themeId;
	private String tableTitle;
	/**
	 * 备注
	 */
	private String tableExplain;
	/**
	 * 命名规则 放在右上角(eg: DC20191204-XXXX  DC20191204-0001 )
	 */
	private String tableCode;
	/**
	 * 当前模板状态 草稿0、已发布1、作废2
	 */
	private String tableStatus;
	/**
	 * 是否隐藏题号
	 */
	private String hiddenCode;
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
	 * 备注
	 */
	private String remarks;
	/**
	 * 删除标志（0否1是）
	 */
	private String delFlag;
	/**
	 * 答题次数限制
	 */
	private Integer answerLimit;
	/**
	 * 答卷分数
	 */
	private Integer answerNum;
	/**
	 * 答卷密码
	 */
	private String answerPwd;

}
