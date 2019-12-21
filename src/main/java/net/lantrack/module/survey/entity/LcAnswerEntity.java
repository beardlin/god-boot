package net.lantrack.module.survey.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 
 *@Author ldahuzi
 *@Date 2019-12-13 14:20:03
 */
@Data
@TableName("lc_answer")
public class LcAnswerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 答卷id
	 */
	@TableId
	private String id;
	/**
	 * 问卷模板id
	 */
	private String themeId;
	/**
	 * 答题时长（s）
	 */
	private Long ansTime;
	/**
	 * 答卷状态（0草稿1已完成）
	 */
	private Integer ansStatus;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;

}
