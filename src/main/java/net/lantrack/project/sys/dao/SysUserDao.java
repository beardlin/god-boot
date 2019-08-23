
package net.lantrack.project.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *@Description 系统用户
 *@Author lantrack
 *@Date 2019/8/23  10:22
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
	
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

}
