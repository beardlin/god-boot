package net.lantrack.module.flow.controller;

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
import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import net.lantrack.module.flow.service.ActNodeRecordService;

/**
 *@Description 节点审批记录
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@RestController
@RequestMapping("flow/actnoderecord")
public class ActNodeRecordController extends BaseController {
    @Autowired
    private ActNodeRecordService actNodeRecordService;

    /**
     * 分页列表
     */
    @RequestMapping("/page")
    @RequiresPermissions("flow:actnoderecord:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        PageEntity page = actNodeRecordService.queryPage(params);
        return getR().result(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("flow:actnoderecord:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
		ActNodeRecordEntity actNodeRecord = actNodeRecordService.getById(id);
        return getR().result(actNodeRecord);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:actnoderecord:save")
    public ReturnEntity save(@RequestBody ActNodeRecordEntity actNodeRecord){
		actNodeRecordService.save(actNodeRecord);
        return getR();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("flow:actnoderecord:update")
    public ReturnEntity update(@RequestBody ActNodeRecordEntity actNodeRecord){
		actNodeRecordService.updateById(actNodeRecord);
        return getR();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:actnoderecord:delete")
    public ReturnEntity delete(@RequestBody Integer[] ids){
		actNodeRecordService.removeByIds(Arrays.asList(ids));
        return getR();
    }

}
