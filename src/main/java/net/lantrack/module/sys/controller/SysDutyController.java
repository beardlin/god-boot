package net.lantrack.module.sys.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysDutyEntity;
import net.lantrack.module.sys.service.SysDutyService;



/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@RestController
@RequestMapping("sys/sysduty")
public class SysDutyController extends BaseController {
    @Autowired
    private SysDutyService sysDutyService;

    /**
     * 分页列表列表
     */
    @RequestMapping("/page")
//    @RequiresPermissions("sys:sysduty:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        PageEntity page = sysDutyService.queryPage(params);
        return getR().result(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysduty:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
		SysDutyEntity sysDuty = sysDutyService.getById(id);
        return getR().result(sysDuty);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysduty:save")
    public ReturnEntity save(@RequestBody SysDutyEntity sysDuty){
		sysDutyService.save(sysDuty);
        return getR();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysduty:update")
    public ReturnEntity update(@RequestBody SysDutyEntity sysDuty){
		sysDutyService.updateById(sysDuty);
        return getR();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysduty:delete")
    public ReturnEntity delete(@RequestBody Integer[] ids){
		sysDutyService.removeByIds(Arrays.asList(ids));
        return getR();
    }

}
