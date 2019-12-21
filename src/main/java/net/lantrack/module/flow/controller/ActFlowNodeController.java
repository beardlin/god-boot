package net.lantrack.module.flow.controller;

import java.util.Arrays;
import java.util.Date;
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
import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import net.lantrack.module.flow.service.ActFlowNodeService;

/**
 *@Description 审批节点表
 *@Author dahuzi
 *@Date 2019-11-05 22:17:05
 */
@RestController
@RequestMapping("flow/actflownode")
public class ActFlowNodeController extends BaseController {
    @Autowired
    private ActFlowNodeService actFlowNodeService;

    /**
     * 分页列表
     */
    @RequestMapping("/page")
    @RequiresPermissions("flow:actflownode:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        PageEntity page = actFlowNodeService.queryPage(params);
        return getR().result(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("flow:actflownode:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
		ActFlowNodeEntity actFlowNode = actFlowNodeService.getById(id);
        return getR().result(actFlowNode);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
   // @RequiresPermissions("flow:actflownode:save")
    public ReturnEntity save(@RequestBody ActFlowNodeEntity actFlowNode){
        actFlowNodeService.save(actFlowNode);
        return  getR().result(actFlowNode);
    }


    /**
     * 修改
     */
    @RequestMapping("/update")
   // @RequiresPermissions("flow:actflownode:update")
    public ReturnEntity update(@RequestBody ActFlowNodeEntity actFlowNode){
        actFlowNode.setCreateBy(null);
        actFlowNode.setCreateDate(null);
        actFlowNode.setFlowId(null);
        actFlowNode.setUpdateDate(new Date());
		actFlowNodeService.updateById(actFlowNode);
       return getR().result(actFlowNode);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{ids}")
//    @RequiresPermissions("flow:actflownode:delete")
    public ReturnEntity delete(@PathVariable("ids") String ids){
		actFlowNodeService.removeByIds(Arrays.asList(ids.split(",")));
        return getR();
    }
    /**
     * 删除
     */
    @RequestMapping("/checkduty")
//    @RequiresPermissions("flow:actflownode:checkduty")
    public ReturnEntity checkduty(@RequestBody ActFlowNodeEntity actFlowNode){
        Boolean aBoolean = actFlowNodeService.checkDuty(actFlowNode);
        if (aBoolean){
            return getR().put("status",0).put("msg","校验通过");
        }else {
            return getR().put("status",1).put("msg","校验失败，当前部门没有此职务");
        }

    }

}
