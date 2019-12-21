
package net.lantrack.module.sys.controller;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.module.sys.entity.SysMenuEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.service.ShiroService;
import net.lantrack.module.sys.service.SysMenuService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 *@Description  系统菜单
 *@Author dahuzi
 *@Date 2019/10/29  17:40
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;



	/**
	 * 导航菜单
	 */
	@GetMapping("/nav")
	public ReturnEntity nav(){
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(user.getUserId());
		Set<String> permissions = shiroService.getUserPermissions(user.getUserId());
		return getR().put("menuList", menuList).put("permissions", permissions);
	}

	/**
	 * 所有菜单分页列表
	 * /sys/menu/page
	 */
	@GetMapping("/page")
	@RequiresPermissions("sys:menu:page")
	public List<SysMenuEntity> page(){
		List<SysMenuEntity> menuList = sysMenuService.list();
		for(SysMenuEntity sysMenuEntity : menuList){
			SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
			if(parentMenuEntity != null){
				sysMenuEntity.setParentName(parentMenuEntity.getName());
			}
		}
		return menuList;
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 * /sys/menu/select
	 */
	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public ReturnEntity select(){
		//查询列表数据
		List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenuEntity root = new SysMenuEntity();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return getR().result(menuList);
	}

	/**
	 * 菜单信息
	 * /sys/menu/info/{menuId}
	 */
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public ReturnEntity info(@PathVariable("menuId") Long menuId){
		SysMenuEntity menu = sysMenuService.getById(menuId);
		return getR().result(menu);
	}

	/**
	 * 保存菜单
	 * /sys/menu/save
	 */
	@SysLog("保存菜单")
	@PostMapping("/save")
	@RequiresPermissions("sys:menu:save")
	public ReturnEntity save(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		sysMenuService.save(menu);
		return getR().suc("添加成功");
	}

	/**
	 * 修改菜单
	 * /sys/menu/update
	 */
	@SysLog("修改菜单")
	@PostMapping("/update")
	@RequiresPermissions("sys:menu:update")
	public ReturnEntity update(@RequestBody SysMenuEntity menu){
		//数据校验
		verifyForm(menu);
		sysMenuService.updateById(menu);
		return getR();
	}

	/**
	 * 删除菜单
	 * /sys/menu/delete/{menuId}
	 */
	@SysLog("删除菜单")
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys:menu:delete")
	public ReturnEntity delete(@PathVariable("menuId") long menuId){
		ReturnEntity r = getR();
		if(menuId <= 31){
			return r.err("系统菜单，不能删除");
		}
		//判断是否有子菜单或按钮
		List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return r.err("请先删除子菜单或按钮");
		}
		sysMenuService.delete(menuId);
		return r;
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenuEntity menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new GlobalException("菜单名称不能为空");
		}
		if(menu.getParentId() == null){
			throw new GlobalException("上级菜单不能为空");
		}
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new GlobalException("菜单URL不能为空");
			}
		}
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new GlobalException("上级菜单只能为目录类型");
			}
			return ;
		}
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new GlobalException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
