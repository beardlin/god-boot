
package net.lantrack.project.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.utils.PageUtils;
import net.lantrack.project.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 *@Description 角色
 *@Author lantrack
 *@Date 2019/8/23  14:20
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
