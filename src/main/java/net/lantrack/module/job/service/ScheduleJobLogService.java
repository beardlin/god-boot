
package net.lantrack.module.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 *@Description 定时任务日志
 *@Author dahuzi
 *@Date 2019/10/29  17:38
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageEntity queryPage(Map<String, Object> params);

}
