package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import lombok.Data;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Data
@TableName("sys_duty")
public class SysDutyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String dName;
	/**
	 * 状态（0正餐1停用）
	 */
	private Integer dStatus;
	/**
	 * 排序
	 */
	private Integer dSort;

}
