
package net.lantrack.project.sys.controller;


import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.project.sys.entity.SysConfigEntity;
import net.lantrack.project.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *@Description 系统配置信息
 *@Author lantrack
 *@Date 2019/8/23  10:17
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {
	@Autowired
	private SysConfigService sysConfigService;
	
	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:config:list")
	public ReturnEntity list(@RequestParam Map<String, Object> params){
		return getR().result(sysConfigService.queryPage(params));
	}
	
	
	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public ReturnEntity info(@PathVariable("id") Long id){

		return getR().result(sysConfigService.getById(id));
	}
	
	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public ReturnEntity save(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);

		sysConfigService.saveConfig(config);
		return getR();
	}
	
	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public ReturnEntity update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		
		sysConfigService.update(config);
		
		return getR();
	}
	
	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public ReturnEntity delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);
		
		return getR();
	}

}
