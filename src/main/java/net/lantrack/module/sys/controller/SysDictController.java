package net.lantrack.module.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import net.lantrack.framework.common.utils.UserUtils;
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
import net.lantrack.module.sys.entity.SysDictEntity;
import net.lantrack.module.sys.service.SysDictService;



/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@RestController
@RequestMapping("sys/sysdict")
public class SysDictController extends BaseController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 字典下拉框
     * sys/sysdict/select/{dictType}
     */
    @RequestMapping("/select/{dictType}")
//    @RequiresPermissions("sys:sysdict:select")
    public ReturnEntity select(@PathVariable("dictType") String dictType){
        return getR().result(sysDictService.getDictSelect(dictType));
    }

    /**
     * 字典分类
     * sys/sysdict/dicts
     */
    @RequestMapping("/dicts")
//    @RequiresPermissions("sys:sysdict:list")
    public ReturnEntity dicts(){
        return getR().result(sysDictService.getDictKind());
    }

    /**
     * 字典分页列表
     * sys/sysdict/page
     */
    @RequestMapping("/page")
//    @RequiresPermissions("sys:sysdict:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        PageEntity page = sysDictService.queryPage(params);
        return getR().result(page);
    }


    /**
     * 信息
     * sys/sysdict/info/{id}
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("sys:sysdict:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
		SysDictEntity sysDict = sysDictService.getById(id);
        return getR().result(sysDict);
    }

    /**
     * 保存
     * sys/sysdict/save
     */
    @RequestMapping("/save")
//    @RequiresPermissions("sys:sysdict:save")
    public ReturnEntity save(@RequestBody String json){
        SysDictEntity sysDict = jsonToObject(json, SysDictEntity.class);
        sysDict.setCreateBy(UserUtils.getUserName());
        sysDict.setCreateTime(new Date());
        sysDict.setUpdateBy(UserUtils.getUserName());
        sysDict.setUpdateTime(new Date());
        sysDictService.save(sysDict);
        return getR();
    }


    /**
     * 修改
     * sys/sysdict/update
     */
    @RequestMapping("/update")
//    @RequiresPermissions("sys:sysdict:update")
    public ReturnEntity update(@RequestBody String json){
        SysDictEntity sysDict = jsonToObject(json, SysDictEntity.class);
        sysDict.setUpdateBy(UserUtils.getUserName());
        sysDict.setUpdateTime(new Date());
		sysDictService.updateById(sysDict);
        return getR();
    }

    /**
     * 删除
     * sys/sysdict/delete
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("sys:sysdict:delete")
    public ReturnEntity delete(@RequestBody Integer[] ids){
		sysDictService.removeByIds(Arrays.asList(ids));
        return getR();
    }

}
