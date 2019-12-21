package net.lantrack.module.sys.controller;

import java.util.*;

import net.lantrack.framework.common.annotation.SysLog;
import net.lantrack.framework.common.component.service.SqlService;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.sys.model.TreeModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysOfficeEntity;
import net.lantrack.module.sys.service.SysOfficeService;



/**
 *@Description 组织机构表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@RestController
@RequestMapping("sys/sysoffice")
public class SysOfficeController extends BaseController {
    @Autowired
    private SysOfficeService sysOfficeService;

    /**
     * 机构树
     * sys/sysoffice/stop
     */
    @RequestMapping("/stop")
//    @RequiresPermissions("sys:sysoffice:stop")
    public ReturnEntity stop(@RequestBody Map<String,Object> parms){
        sysOfficeService.stop(parms);
        return getR();
    }
    /**
     * 机构树
     * sys/sysoffice/tree
     */
    @RequestMapping("/tree")
//    @RequiresPermissions("sys:sysoffice:list")
    public ReturnEntity list(){
        return getR().result(sysOfficeService.getTree());
    }

    /**
     * 分页列表
     * sys/sysoffice/page
     */
    @RequestMapping("/page")
//    @RequiresPermissions("sys:sysoffice:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
//        PageEntity page = sysOfficeService.queryPage(params);
        return getR().result(sysOfficeService.queryByPid(params));
    }


    /**
     * 机构详情
     * sys/sysoffice/info/{id}
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("sys:sysoffice:info")
    public ReturnEntity info(@PathVariable("id") Long id){
		SysOfficeEntity sysOffice = sysOfficeService.getById(id);
        return getR().result(sysOffice);
    }

    /**
     * 保存
     * sys/sysoffice/save
     */
    @SysLog("机构添加")
    @PostMapping("/save")
//    @RequiresPermissions("sys:sysoffice:save")
    public ReturnEntity save(@RequestBody String json){
        SysOfficeEntity sysOffice = jsonToObject(json, SysOfficeEntity.class);
        sysOffice.setCreateBy(UserUtils.getUserId()+"");
        sysOffice.setCreateTime(new Date());
        sysOffice.setUpdateBy(UserUtils.getUserId()+"");
        sysOffice.setUpdateTime(new Date());
		sysOfficeService.save(sysOffice);
        return getR();
    }

    /**
     * 修改
     * sys/sysoffice/update
     */
    @RequestMapping("/update")
//    @RequiresPermissions("sys:sysoffice:update")
    public ReturnEntity update(@RequestBody String json){
        SysOfficeEntity sysOffice = jsonToObject(json, SysOfficeEntity.class);
        sysOffice.setUpdateBy(UserUtils.getUserId()+"");
        sysOffice.setUpdateTime(new Date());
		sysOfficeService.updateById(sysOffice);
        return getR();
    }

    /**
     * 删除
     * sys/sysoffice/delete/{id}
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("sys:sysoffice:delete")
    public ReturnEntity delete(@PathVariable("id") Long id){
        ReturnEntity r = getR();
        if(id==1){
            return r.err("根节点不可删除");
        }
        Map<String,Object> parms = new HashMap<>();
        parms.put("pid",id);
        List<SysOfficeEntity> list = sysOfficeService.queryByPid(parms);
        if(!list.isEmpty()){
            return r.err("请先删除子节点");
        }
        sysOfficeService.removeById(id);
        return r;
    }

}
