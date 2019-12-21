package net.lantrack.module.flow.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import net.lantrack.module.flow.model.ActFlowInfoResp;
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
import net.lantrack.module.flow.entity.ActFlowEntity;
import net.lantrack.module.flow.service.ActFlowService;



/**
 *@Description 流程定义表
 *@Author dahuzi
 *@Date 2019-11-05 22:17:05
 */
@RestController
@RequestMapping("flow/actflow")
public class ActFlowController extends BaseController {
    @Autowired
    private ActFlowService actFlowService;

    /**
     * 分页列表
     */
    @RequestMapping("/page")
    @RequiresPermissions("flow:actflow:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        PageEntity page = actFlowService.queryPage(params);
        return getR().result(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("flow:actflow:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
        ActFlowInfoResp actFlow = actFlowService.info(id);
        return getR().result(actFlow);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:actflow:save")
    public ReturnEntity save(@RequestBody ActFlowEntity actFlow){
		actFlowService.save(actFlow);
        return getR();
    }

    /**
     * 发布
     */
    @RequestMapping("/publish")
    //@RequiresPermissions("flow:actflow:publish")
    public ReturnEntity publish(@RequestBody ActFlowEntity actFlow){
        ActFlowEntity act = new ActFlowEntity();
        act.setId(actFlow.getId());
        act.setFlowStatus(actFlow.getFlowStatus());
        act.setPublishDate(new Date());
		actFlowService.updateById(act);
        return getR();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("flow:actflow:update")
    public ReturnEntity update(@RequestBody ActFlowEntity actFlow){
        actFlow.setCreateBy(null);
        actFlow.setCreateDate(null);
        actFlow.setFlowStatus(null);
        actFlow.setUpdateDate(new Date());
		actFlowService.updateById(actFlow);
        return getR();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:actflow:delete")
    public ReturnEntity delete(@RequestBody Integer[] ids){
		actFlowService.removeByIds(Arrays.asList(ids));
        return getR();
    }

    //可以提交的申请
}
