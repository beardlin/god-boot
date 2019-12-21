
package net.lantrack.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.module.sys.entity.SysUserRoleEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description  用户与角色
 *@Author dahuzi
 *@Date 2019/10/29  17:43
 */
@Mapper
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity> {

	/**
	 * 删除角色用户绑定
	 * @param roleId
	 * @param userId
	 */
	@Delete("delete from sys_user_role where user_id = #{userId} and role_id = #{roleId}")
	void deleteUserRole(@Param("roleId") String roleId,@Param("userId") String userId);

	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);


	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
