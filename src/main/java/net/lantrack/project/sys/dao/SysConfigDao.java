
package net.lantrack.project.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@Description 系统配置信息
 *@Author lantrack
 *@Date 2019/8/23  10:24
 */
@Mapper
public interface SysConfigDao extends BaseMapper<SysConfigEntity> {

	/**
	 * 根据key，查询value
	 */
	SysConfigEntity queryByKey(String paramKey);

	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);
	
}
