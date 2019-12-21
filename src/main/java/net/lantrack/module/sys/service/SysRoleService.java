
package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysRoleEntity;
import net.lantrack.module.sys.model.UserRoleDetailModel;
import net.lantrack.module.sys.model.UserRoleModel;

import java.util.List;
import java.util.Map;
/**
 *@Description  角色
 *@Author dahuzi
 *@Date 2019/10/29  17:52
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	/**
	 * 获取用户角色详情
	 * @param userId
	 * @return
	 */
	UserRoleDetailModel getUserRoleDetail(Long userId);

	/**
	 * 角色配置权限
	 * @param parms
	 */
	void roleAuth(Map<String,String> parms);

	/**
	 * 删除当前角色下的用户
	 * @param parms
	 */
	void deleteUserByRoleId(Map<String,String> parms);

	/**
	 * 用户角色绑定配置
	 * @param parms
	 */
	void configUserRole(Map<String,String> parms);

	/**
	 * 获取当前角色下的用户列表
	 * @param parms
	 * @return
	 */
	List<UserRoleModel> getUserListByRoleId(Map<String,Object> parms);

	PageEntity queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);


	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

	/**
	 * 查询当前角色分类下的角色
	 * @param typeId
	 * @return
	 */
	List<SysRoleEntity> queryListByTypeId(Integer typeId);
}
