
package net.lantrack.project.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *@Description 角色管理
 *@Author lantrack
 *@Date 2019/8/23  10:23
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
