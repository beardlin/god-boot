
package net.lantrack.project.job.controller;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.project.job.entity.ScheduleJobEntity;
import net.lantrack.project.job.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *@Description 定时任务
 *@Author lantrack
 *@Date 2019/8/22  18:19
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController extends BaseController {
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public ReturnEntity list(@RequestParam Map<String, Object> params){
		PageEntity page = scheduleJobService.queryPage(params);
		return getR().result(page);
	}
	
	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public ReturnEntity info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.getById(jobId);
		return getR().result(schedule);
	}

	
	/**
	 * 保存定时任务
	 */
	@SysLog("保存定时任务")
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public ReturnEntity save(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
		scheduleJobService.saveJob(scheduleJob);
		return getR();
	}
	
	/**
	 * 修改定时任务
	 */
	@SysLog("修改定时任务")
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public ReturnEntity update(@RequestBody ScheduleJobEntity scheduleJob){
		ValidatorUtils.validateEntity(scheduleJob);
		scheduleJobService.update(scheduleJob);
		return getR();
	}
	
	/**
	 * 删除定时任务
	 */
	@SysLog("删除定时任务")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public ReturnEntity delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		
		return getR();
	}
	
	/**
	 * 立即执行任务
	 */
	@SysLog("立即执行任务")
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public ReturnEntity run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		
		return getR();
	}
	
	/**
	 * 暂停定时任务
	 */
	@SysLog("暂停定时任务")
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public ReturnEntity pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		
		return getR();
	}
	
	/**
	 * 恢复定时任务
	 */
	@SysLog("恢复定时任务")
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public ReturnEntity resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		
		return getR();
	}

}
