
package net.lantrack.project.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.project.sys.entity.SysUserRoleEntity;

import java.util.List;


/**
 *@Description 用户与角色
 *@Author lantrack
 *@Date 2019/8/23  14:20
 */
public interface SysUserRoleService extends IService<SysUserRoleEntity> {
	
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
