
package net.lantrack.project.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.job.entity.ScheduleJobLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
@Mapper
public interface ScheduleJobLogDao extends BaseMapper<ScheduleJobLogEntity> {
	
}
