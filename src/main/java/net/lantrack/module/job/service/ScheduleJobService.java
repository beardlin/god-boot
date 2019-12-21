
package net.lantrack.module.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.job.entity.ScheduleJobEntity;

import java.util.Map;

/**
 *@Description  定时任务
 *@Author dahuzi
 *@Date 2019/10/29  17:31
 */
public interface ScheduleJobService extends IService<ScheduleJobEntity> {

	PageEntity queryPage(Map<String, Object> params);

	/**
	 * 保存定时任务
	 */
	void saveJob(ScheduleJobEntity scheduleJob);

	/**
	 * 更新定时任务
	 */
	void update(ScheduleJobEntity scheduleJob);

	/**
	 * 批量删除定时任务
	 */
	void deleteBatch(Long[] jobIds);

	/**
	 * 批量更新定时任务状态
	 */
	int updateBatch(Long[] jobIds, int status);

	/**
	 * 立即执行
	 */
	void run(Long[] jobIds);

	/**
	 * 暂停运行
	 */
	void pause(Long[] jobIds);

	/**
	 * 恢复运行
	 */
	void resume(Long[] jobIds);
}
