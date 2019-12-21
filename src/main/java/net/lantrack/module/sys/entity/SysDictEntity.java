package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Data
@TableName("sys_dict")
public class SysDictEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 字典key
	 */
	private String dKey;
	/**
	 * 字典value
	 */
	private String dVal;
	/**
	 * 字典分类
	 */
	private String dType;
	/**
	 * 分类描述
	 */
	private String dLable;
	/**
	 * 字典排序
	 */
	private Integer dSort;
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
	 * 删除标识（0否1是）
	 */
	private Integer delFlag;

}
