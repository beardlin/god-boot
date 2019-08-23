
package net.lantrack.project.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.project.sys.entity.SysRoleMenuEntity;

import java.util.List;


/**
 *@Description 角色与菜单
 *@Author lantrack
 *@Date 2019/8/23  14:21
 */
public interface SysRoleMenuService extends IService<SysRoleMenuEntity> {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
	
}
