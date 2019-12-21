
package net.lantrack.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.module.sys.entity.SysRoleEntity;
import net.lantrack.module.sys.model.UserRoleModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description  角色管理
 *@Author dahuzi
 *@Date 2019/10/29  17:43
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {

	/**
	 * 获取当前用户的角色
	 * @param userId
	 * @return
	 */
	List<String> getRoleNameByUserId(@Param("userId") String userId);
	/**
	 * 获取当前角色下的用户列表
	 * @param user
	 * @param roleId
	 * @return
	 */
	List<UserRoleModel> getUserListByRoleId(@Param("user") String user,@Param("roleId") String roleId);

	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
