
package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.module.sys.entity.SysUserTokenEntity;

/**
 *@Description  用户Token
 *@Author dahuzi
 *@Date 2019/10/29  17:52
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 刷新token
	 * @param token
	 */
	void updateToken(String token);

	/**
	 * 保存token
	 * @param userId  用户ID
	 */
	ReturnEntity createToken(String token,long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
