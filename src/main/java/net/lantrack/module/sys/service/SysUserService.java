
package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.MapEntity;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import java.util.List;
import java.util.Map;
/**
 *@Description  系统用户
 *@Author dahuzi
 *@Date 2019/10/29  17:52
 */
public interface SysUserService extends IService<SysUserEntity> {

	/**
	 * 获取用户下拉或者checkbox
	 * @param parms
	 * @return
	 */
	List<MapEntity> getUserMap(Map<String,String> parms);

	PageEntity queryPage(Map<String, Object> params);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	/**
	 * 保存用户
	 */
	void saveUser(SysUserEntity user);

	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);

	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	boolean updatePassword(Long userId, String password, String newPassword);

	/**
	 * 用户禁用启用,锁定解锁
	 * @param userId
	 * @param status
	 */
	void statusChange(Long userId,String status);


	/**
	 *  查询指定部门组织的人员数量
	 * @param officeId 部门id
	 * @param dutyId 职务id
	 * @return 组织结构下是否有这个职务的人员数量
	 */
	Integer queryDutyCount(Long officeId, String dutyId);

	/**
	 *
	 * @param officeId
	 * @return
	 */
	String queryMasterId(Long officeId);
}
