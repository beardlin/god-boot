
package net.lantrack.project.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.project.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 *@Description 定时任务日志
 *@Author lantrack
 *@Date 2019/8/23  9:09
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageEntity queryPage(Map<String, Object> params);
	
}
