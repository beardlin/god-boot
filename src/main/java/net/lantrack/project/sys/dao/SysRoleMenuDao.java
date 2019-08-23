
package net.lantrack.project.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *@Description 角色与菜单
 *@Author lantrack
 *@Date 2019/8/23  10:23
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
