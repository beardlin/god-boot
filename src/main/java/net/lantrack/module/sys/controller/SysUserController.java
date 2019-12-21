
package net.lantrack.module.sys.controller;

import cn.hutool.core.bean.BeanUtil;
import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.framework.common.validator.Assert;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.framework.common.validator.group.AddGroup;
import net.lantrack.framework.common.validator.group.UpdateGroup;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.form.PasswordForm;
import net.lantrack.module.sys.model.SysUserModel;
import net.lantrack.module.sys.service.SysUserRoleService;
import net.lantrack.module.sys.service.SysUserService;
import net.lantrack.module.sys.service.SysUserTokenService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *@Description  用户管理
 *@Author dahuzi
 *@Date 2019/10/29  17:39
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	/**
	 * 系统默认密码
	 */
	public static  final String DEFAULT_PASS = "123456";



	/**
	 * 获取用户下拉框
	 * /sys/user/select
	 */
	@RequestMapping("/select")
	public ReturnEntity select(@RequestBody Map<String,String > parms){
		return getR().result(sysUserService.getUserMap(parms));
	}

	/**
	 * 用户踢出
	 * /sys/user/unline/{userId}
	 */
	@GetMapping("/unline")
	@RequiresPermissions("sys:user:unline")
	@SysLog("踢出用户")
	public ReturnEntity unline(@PathVariable("userId") Long userId){
		//在当前系统失效
		sysUserTokenService.logout(userId);
		return getR().suc("操作成功");
	}

	/**
	 * 用户禁用，解禁
	 * /sys/user/disable
	 */
	@PostMapping("/disable")
	@RequiresPermissions("sys:user:disable")
	@SysLog("禁用/解禁")
	public ReturnEntity disable(@RequestBody Map<String,Object> parms){
		Long userId = (Long) parms.get("userId");
		Object status = parms.get("status");
		if("0".equals(status)){//禁用
			//在当前系统失效
			sysUserTokenService.logout(userId);
		}
		sysUserService.statusChange(userId,status.toString());
		return getR().suc("操作成功");
	}

	/**
	 * 用户锁定（禁止登录）
	 * /sys/user/unlock/{userId}
	 */
	@GetMapping("/lock/{userId}")
	@RequiresPermissions("sys:user:lock")
	@SysLog("锁定用户")
	public ReturnEntity lock(@PathVariable("userId") Long userId){
		SysUserEntity entity = sysUserService.getById(userId);
		ReturnEntity r = getR();
		if("0".equals(entity.getStatus())){
			r.err("当前用户已停用，不可操作");
		}else if("1".equals(entity.getStatus())){//1为正常，锁定改为2
			sysUserService.statusChange(userId,"2");
		}
		return r.suc("锁定成功");
	}

	/**
	 * 用户解锁（解除禁止登录）
	 * /sys/user/unlock/{userId}
	 */
	@GetMapping("/unlock/{userId}")
	@RequiresPermissions("sys:user:unlock")
	@SysLog("解锁用户")
	public ReturnEntity unlock(@PathVariable("userId") Long userId){
		SysUserEntity entity = sysUserService.getById(userId);
		ReturnEntity r = getR();
		if("0".equals(entity.getStatus())){
			r.err("当前用户已停用，不可操作");
		}else if("2".equals(entity.getStatus())){//2为锁定状态，解锁改为1正常
			sysUserService.statusChange(userId,"1");
		}
		return r.suc("解锁成功");
	}

	/**
	 * 所有用户列表
	 * /sys/user/page
	 */
	@PostMapping("/page")
	@RequiresPermissions("sys:user:page")
	public ReturnEntity page(@RequestBody Map<String, Object> params){
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		//只有超级管理员，才能查看所有管理员列表
		if(user.getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", user.getUserId());
		}
		return getR().result(sysUserService.queryPage(params));
	}

	/**
	 * 获取登录的用户信息
	 * /sys/user/mine
	 */
	@GetMapping("/mine")
	public ReturnEntity mine(){
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		user.setPassword("");
		return getR().result(user);
	}

	@SysLog("修改个人信息")
	@PostMapping("/center")
//	@RequiresPermissions("sys:user:update")
	public ReturnEntity centerUpdate(@RequestBody SysUserEntity user){
		SysUserEntity entity = UserUtils.getUserEntity();
		entity.setMobile(user.getMobile());
		entity.setRealName(user.getRealName());
		entity.setEmail(user.getEmail());
		entity.setSex(user.getSex());
		entity.setBirthday(user.getBirthday());
		entity.setIdCard(user.getIdCard());
		entity.setQqNum(user.getQqNum());
		entity.setWeiChart(user.getWeiChart());
		sysUserService.update(entity);
		return getR().suc("修改成功");
	}

	/**
	 * 密码重置
	 * /sys/user/reset/{userId}
	 */
	@SysLog("重置密码")
	@PostMapping("/reset/{userId}")
	public ReturnEntity reset(@PathVariable("userId") Long userId){
		SysUserEntity user = this.sysUserService.getById(userId);
		//sha256加密
		String newPassword = new Sha256Hash(DEFAULT_PASS, user.getSalt()).toHex();
		//重置密码
		sysUserService.updatePassword(user.getUserId(), user.getPassword(), newPassword);
		//在当前系统失效
		sysUserTokenService.logout(user.getUserId());
		return getR().suc("重置成功");
	}
	/**
	 * 修改登录用户密码
	 * /sys/user/password
	 */
	@SysLog("修改密码")
	@PostMapping("/password")
	public ReturnEntity password(@RequestBody PasswordForm form){
		Assert.isBlank(form.getNewPassword(), "新密码不为能空");
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		//sha256加密
		String password = new Sha256Hash(form.getPassword(), user.getSalt()).toHex();
		//sha256加密
		String newPassword = new Sha256Hash(form.getNewPassword(), user.getSalt()).toHex();

		//更新密码
		boolean flag = sysUserService.updatePassword(user.getUserId(), password, newPassword);
		ReturnEntity r = getR();
		if(!flag){
			return r.err("原密码不正确");
		}
		//在当前系统失效
		sysUserTokenService.logout(user.getUserId());
		return r;
	}

	/**
	 * 用户信息
	 * /sys/user/info/{userId}
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public ReturnEntity info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		SysUserModel model = new SysUserModel();
		BeanUtil.copyProperties(user,model);
		return getR().result(model);
	}

	/**
	 * 保存用户
	 * /sys/user/save
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public ReturnEntity save(@RequestBody SysUserEntity user){
		user.setPassword(DEFAULT_PASS);
		ValidatorUtils.validateEntity(user, AddGroup.class);
		SysUserEntity currentUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		user.setCreateUserId(currentUser.getUserId());
		sysUserService.saveUser(user);

		return getR();
	}

	/**
	 * 修改用户
	 * /sys/user/update
	 */
	@SysLog("修改用户")
	@PostMapping("/update")
	@RequiresPermissions("sys:user:update")
	public ReturnEntity update(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, UpdateGroup.class);
		SysUserEntity currentUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		user.setCreateUserId(currentUser.getUserId());
		sysUserService.update(user);
		return getR();
	}

	/**
	 * 删除用户
	 * /sys/user/delete
	 */
	@SysLog("删除用户")
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public ReturnEntity delete(@RequestBody Long[] userIds){
		ReturnEntity r = getR();
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		if(ArrayUtils.contains(userIds, 1L)){
			return r.err("系统管理员不能删除");
		}

		if(ArrayUtils.contains(userIds, user.getUserId())){
			return r.err("当前用户不能删除");
		}
		sysUserService.deleteBatch(userIds);
		return r;
	}
}
