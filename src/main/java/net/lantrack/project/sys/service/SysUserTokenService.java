
package net.lantrack.project.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.utils.R;
import net.lantrack.project.sys.entity.SysUserTokenEntity;

/**
 *@Description 用户Token
 *@Author lantrack
 *@Date 2019/8/23  10:28
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
