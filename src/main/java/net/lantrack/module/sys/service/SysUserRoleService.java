
package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.module.sys.entity.SysUserRoleEntity;
import java.util.List;

/**
 *@Description  用户与角色
 *@Author dahuzi
 *@Date 2019/10/29  17:52
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {

	/**
	 * 删除用户和角色关联
	 * @param roleId
	 * @param userId
	 */
	void deleteUserRole(String roleId,String userId);

	void saveOrUpdate(Long userId, List<Long> roleIdList);

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
