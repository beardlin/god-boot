
package net.lantrack.project.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *@Description 系统日志
 *@Author lantrack
 *@Date 2019/8/23  10:24
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
