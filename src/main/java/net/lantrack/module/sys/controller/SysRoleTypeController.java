package net.lantrack.module.sys.controller;

import java.util.Date;
import java.util.List;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.sys.entity.SysRoleEntity;
import net.lantrack.module.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.module.sys.entity.SysRoleTypeEntity;
import net.lantrack.module.sys.service.SysRoleTypeService;



/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-19 20:50:07
 */
@RestController
@RequestMapping("sys/sysroletype")
public class SysRoleTypeController extends BaseController {
    @Autowired
    private SysRoleTypeService sysRoleTypeService;

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页列表
     * sys/sysroletype/page
     */
    @RequestMapping("/page")
//    @RequiresPermissions("sys:sysroletype:page")
    public ReturnEntity page(){
        return getR().result(sysRoleTypeService.getList());
    }


    /**
     * 信息
     * sys/sysroletype/info/{id}
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("sys:sysroletype:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
		SysRoleTypeEntity sysRoleType = sysRoleTypeService.getById(id);
        return getR().result(sysRoleType);
    }

    /**
     * 保存
     * sys/sysroletype/save
     */
    @SysLog("添加角色分类")
    @RequestMapping("/save")
//    @RequiresPermissions("sys:sysroletype:save")
    public ReturnEntity save(@RequestBody SysRoleTypeEntity sysRoleType){
        sysRoleType.setCreateBy(UserUtils.getUserName());
        sysRoleType.setCreateTime(new Date());
		sysRoleTypeService.save(sysRoleType);
        return getR();
    }

    /**
     * 修改
     * sys/sysroletype/update
     */
    @RequestMapping("/update")
//    @RequiresPermissions("sys:sysroletype:update")
    public ReturnEntity update(@RequestBody SysRoleTypeEntity sysRoleType){
		sysRoleTypeService.updateById(sysRoleType);
        return getR();
    }

    /**
     * 删除
     * sys/sysroletype/update/{id}
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("sys:sysroletype:delete")
    public ReturnEntity delete(@PathVariable("id") Integer id){
        List<SysRoleEntity> roles = sysRoleService.queryListByTypeId(id);
        if(!roles.isEmpty()){
            return  getR().err("当前分类下有"+roles.size()+"个角色，不可删除");
        }
        sysRoleTypeService.removeById(id);
        return getR();
    }

}
