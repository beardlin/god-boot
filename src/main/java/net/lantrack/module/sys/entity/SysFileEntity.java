package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 *@Description 系统附件表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Data
@TableName("sys_file")
public class SysFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 原始名称
	 */
	private String oldName;
	/**
	 * 存放路径
	 */
	private String filePath;
	/**
	 * 使用状态（0垃圾1已挂载）
	 */
	private Integer useStatus;
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件大小（byte）
	 */
	private Long fileSize;
	/**
	 * 上传时间
	 */
	private Date uploadTime;
	/**
	 * 上传人
	 */
	private String uploadBy;

}
