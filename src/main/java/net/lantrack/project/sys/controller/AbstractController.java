
package net.lantrack.project.sys.controller;

import net.lantrack.project.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@Description BaseController
 *@Author lantrack
 *@Date 2019/8/23  10:18
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
}
