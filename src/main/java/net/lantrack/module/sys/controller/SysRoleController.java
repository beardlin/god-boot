
package net.lantrack.module.sys.controller;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.framework.common.validator.ValidatorUtils;
import net.lantrack.module.sys.entity.SysRoleEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.service.SysRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Description  角色管理
 *@Author dahuzi
 *@Date 2019/10/29  17:40
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService sysRoleService;


	/**
	 * 用户角色详情
	 * /sys/role/userrole/{userid}
	 */
	@PostMapping("/userrole/{userid}")
	public ReturnEntity userrole(@PathVariable("userid") Long userid){
		return getR().result(sysRoleService.getUserRoleDetail(userid));
	}

	/**
	 * 角色配置权限
	 * /sys/role/auth
	 */
	@PostMapping("/auth")
	public ReturnEntity auth(@RequestBody Map<String,String> parms){
		this.sysRoleService.roleAuth(parms);
		return getR();
	}

	/**
	 * 用户配置角色
	 * /sys/role/config
	 */
	@PostMapping("/config")
	public ReturnEntity config(@RequestBody Map<String,String> parms){
		this.sysRoleService.configUserRole(parms);

		return getR();
	}

	/**
	 * 删除当前角色下的用户
	 *	/sys/role/userdel
	 */
	@PostMapping("/userdel")
	public ReturnEntity userdel(@RequestBody Map<String,String> parms){
		this.sysRoleService.deleteUserByRoleId(parms);
		return getR();
	}

	/**
	 * 获取当前角色下的用户列表
	 *	/sys/role/userlist
	 */
	@PostMapping("/userlist")
	public ReturnEntity userlist(@RequestBody Map<String,Object> parms){
		return getR().result(sysRoleService.getUserListByRoleId(parms));
	}

	/**
	 * 角色分页列表
	 * /sys/role/list
	 */
	@PostMapping("/page")
	@RequiresPermissions("sys:role:page")
	public ReturnEntity page(@RequestBody Map<String, Object> params){
		SysUserEntity user = UserUtils.getUserEntity();
		//如果不是超级管理员，则只查询自己创建的角色列表
		if(user.getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", user.getUserId() );
		}
		return getR().result(sysRoleService.queryPage(params));
	}

	/**
	 * 角色下拉列表
	 * /sys/role/select
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
	 * /sys/role/info/{roleId}
	 */
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public ReturnEntity info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.getById(roleId);
		if(role==null){
			return  getR().err("角色不存在");
		}
		//查询角色对应的菜单
//		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
//		role.setMenuIdList(menuIdList);

		return getR().result(role);
	}

	/**
	 * 保存角色
	 * /sys/role/save
	 */
	@SysLog("保存角色")
	@PostMapping("/save")
	@RequiresPermissions("sys:role:save")
	public ReturnEntity save(@RequestBody SysRoleEntity role){
		validate(role);
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		role.setCreateUserId(user.getUserId());
		//如果选择了拷贝，则拷贝选择角色的权限到当前角色中
		if(role.getCopy()){
			SysRoleEntity copyRole = sysRoleService.getById(role.getCopyId());
			role.setPermission(copyRole.getPermission());
		}
		sysRoleService.saveRole(role);
		return getR();
	}

	/**
	 * 修改角色
	 * /sys/role/update
	 */
	@SysLog("修改角色")
	@PostMapping("/update")
	@RequiresPermissions("sys:role:update")
	public ReturnEntity update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		role.setCreateUserId(user.getUserId());
		//如果选择了拷贝，则拷贝选择角色的权限到当前角色中
		if(role.getCopy()){
			SysRoleEntity copyRole = sysRoleService.getById(role.getRoleId());
			role.setPermission(copyRole.getPermission());
		}
		sysRoleService.update(role);
		return getR();
	}

	/**
	 * 删除角色
	 * /sys/role/delete/{id}
	 */
	@SysLog("删除角色")
	@PostMapping("/delete/{id}")
//	@RequiresPermissions("sys:role:delete")
	public ReturnEntity delete(@PathVariable("id") Long roleId){
		sysRoleService.removeById(roleId);
		return getR();
	}
}
