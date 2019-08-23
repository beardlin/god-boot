
package net.lantrack.project.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 *@Description 定时任务
 *@Author lantrack
 *@Date 2019/8/22  18:27
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
