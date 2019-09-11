
package net.lantrack.project.sys.controller;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.project.sys.entity.SysRoleEntity;
import net.lantrack.project.sys.entity.SysUserEntity;
import net.lantrack.project.sys.service.SysRoleMenuService;
import net.lantrack.project.sys.service.SysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Description 角色管理
 *@Author lantrack
 *@Date 2019/8/23  10:11
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;


	/**
	 * 角色列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public ReturnEntity list(@RequestParam Map<String, Object> params){
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(user.getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", user.getUserId() );
		}


		return getR().result(sysRoleService.queryPage(params));
	}
	
	/**
	 * 角色列表
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public ReturnEntity select(){
		Map<String, Object> map = new HashMap<>();
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		//如果不是超级管理员，则只查询自己所拥有的角色列表
		if(user.getUserId() != Constant.SUPER_ADMIN){
			map.put("create_user_id", user.getUserId());
		}
		List<SysRoleEntity> list = (List<SysRoleEntity>) sysRoleService.listByMap(map);
		
		return getR().result(list);
	}
	
	/**
	 * 角色信息
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public ReturnEntity info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return getR().result(role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public ReturnEntity save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		role.setCreateUserId(user.getUserId());
		sysRoleService.saveRole(role);
		
		return getR();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public ReturnEntity update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		role.setCreateUserId(user.getUserId());
		sysRoleService.update(role);
		
		return getR();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public ReturnEntity delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return getR();
	}
}
