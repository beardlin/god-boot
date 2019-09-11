
package net.lantrack.project.sys.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.project.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 *@Description 系统日志
 *@Author lantrack
 *@Date 2019/8/23  10:15
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sys:log:list")
	public ReturnEntity list(@RequestParam Map<String, Object> params){

		return getR().result(sysLogService.queryPage(params));
	}
	
}
