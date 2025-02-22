
package net.lantrack.module.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 *@Description 系统用户Token
 *@Author dahuzi
 *@Date 2019/10/29  17:46
 */
@Data
@TableName("sys_user_token")
public class SysUserTokenEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//用户ID
	@TableId(type = IdType.INPUT)
	private Long userId;
	//token
	private String token;
	//过期时间
	private Date expireTime;
	//更新时间
	private Date updateTime;


}
