
package net.lantrack.module.sys.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.module.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 *@Description  系统日志
 *@Author dahuzi
 *@Date 2019/10/29  17:40
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 分页列表
	 */
	@PostMapping("/page")
	@RequiresPermissions("sys:log:page")
//	@SysLog("查看日志")
	public ReturnEntity page(@RequestBody Map<String, Object> params){

		return getR().result(sysLogService.queryPage(params));
	}

}
