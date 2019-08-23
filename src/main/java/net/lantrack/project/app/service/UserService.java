
package net.lantrack.project.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.project.app.entity.UserEntity;
import net.lantrack.project.app.form.LoginForm;

/**
 *@Description 用户管理
 *@Author lantrack
 *@Date 2019/8/22  17:18
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(LoginForm form);
}
