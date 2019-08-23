
package net.lantrack.project.job.controller;

import net.lantrack.framework.common.utils.PageUtils;
import net.lantrack.framework.common.utils.R;
import net.lantrack.project.job.entity.ScheduleJobLogEntity;
import net.lantrack.project.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *@Description 定时任务日志
 *@Author lantrack
 *@Date 2019/8/22  18:21
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = scheduleJobLogService.queryPage(params);
		return R.ok().put("page", page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		return R.ok().put("log", log);
	}
}
