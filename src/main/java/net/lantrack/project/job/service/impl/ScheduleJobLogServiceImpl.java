
package net.lantrack.project.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.utils.PageUtils;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.project.job.dao.ScheduleJobLogDao;
import net.lantrack.project.job.entity.ScheduleJobLogEntity;
import net.lantrack.project.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 *@Description 定时任务日志管理
 *@Author lantrack
 *@Date 2019/8/23  9:10
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String jobId = (String)params.get("jobId");

		IPage<ScheduleJobLogEntity> page = this.page(
			new Query<ScheduleJobLogEntity>().getPage(params),
			new QueryWrapper<ScheduleJobLogEntity>().like(StringUtils.isNotBlank(jobId),"job_id", jobId)
		);

		return new PageUtils(page);
	}

}
