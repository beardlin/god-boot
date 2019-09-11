
package net.lantrack.project.sys.controller;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.validator.Assert;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.framework.common.validator.group.AddGroup;
import net.lantrack.framework.common.validator.group.UpdateGroup;
import net.lantrack.project.sys.entity.SysUserEntity;
import net.lantrack.project.sys.form.PasswordForm;
import net.lantrack.project.sys.service.SysUserRoleService;
import net.lantrack.project.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 *@Description 用户管理
 *@Author lantrack
 *@Date 2019/8/23  10:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;


	/**
	 * 所有用户列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public ReturnEntity list(@RequestParam Map<String, Object> params){
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		//只有超级管理员，才能查看所有管理员列表
		if(user.getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", user.getUserId());
		}
		return getR().result(sysUserService.queryPage(params));
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public ReturnEntity info(){
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		return getR().result(user);
	}
	
	/**
	 * 修改登录用户密码
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
		return r;
	}
	
	/**
	 * 用户信息
	 */
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public ReturnEntity info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.getById(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return getR().result(user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public ReturnEntity save(@RequestBody SysUserEntity user){
		ValidatorUtils.validateEntity(user, AddGroup.class);
		SysUserEntity currentUser = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		user.setCreateUserId(currentUser.getUserId());
		sysUserService.saveUser(user);
		
		return getR();
	}
	
	/**
	 * 修改用户
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
