
package net.lantrack.project.job.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.entity.ReturnEntity;
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
public class ScheduleJobLogController extends BaseController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public ReturnEntity list(@RequestParam Map<String, Object> params){
		PageEntity page = scheduleJobLogService.queryPage(params);
		return getR().result(page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public ReturnEntity info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		return getR().result(log);
	}
}
