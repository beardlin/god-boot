
package net.lantrack.module.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.module.sys.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@Description  系统配置信息
 *@Author dahuzi
 *@Date 2019/10/29  17:44
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
