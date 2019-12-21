
package net.lantrack.module.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.module.job.entity.ScheduleJobEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 *@Description 定时任务
 *@Author 大胡子
 *@Date 2019/10/28  18:19
 */
@Mapper
public interface ScheduleJobDao extends BaseMapper<ScheduleJobEntity> {

	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
