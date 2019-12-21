
package net.lantrack.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.framework.common.entity.MapEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 *@Description  系统用户
 *@Author dahuzi
 *@Date 2019/10/29  17:43
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {

	@Update("update sys_user set status = #{stat} where user_id = #{userId} ")
	void changeStatus(@Param("userId") Long userId,@Param("stat") String stat);

	@Select("select user_id,real_name from sys_user where office_id = #{officeId} ")
	List<Map<String,Object>> getUserSelect(@Param("officeId") String officeId);

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
	 * 根据用户名或手机号，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

}
