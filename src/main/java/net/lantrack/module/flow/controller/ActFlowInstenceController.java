package net.lantrack.module.flow.controller;

import java.util.Arrays;
import java.util.Map;

import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import net.lantrack.module.flow.enums.FlowTableNameEnum;
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
import net.lantrack.module.flow.entity.ActFlowInstenceEntity;
import net.lantrack.module.flow.service.ActFlowInstenceService;



/**
 *@Description 流程实例表
 *@Author dahuzi
 *@Date 2019-11-05 22:17:05
 */
@RestController
@RequestMapping("flow/actflowinstence")
public class ActFlowInstenceController extends BaseController {
    @Autowired
    private ActFlowInstenceService actFlowInstenceService;

    //代办事项
    //历史审批
    //我的申请
    /**
     *  发布流程实例提交申请
     */
    @RequestMapping("/submit/{flowInstanceId}")
   // @RequiresPermissions("flow:actflowinstence:submit")
    public ReturnEntity submit(@PathVariable("flowInstanceId") Integer  flowInstanceId){
        actFlowInstenceService.publish(flowInstanceId);
        return getR();
    }

    @RequestMapping("/audit")
    //@RequiresPermissions("flow:actflowinstence:audit")
    public ReturnEntity audit(@RequestBody ActNodeRecordEntity entity){
        actFlowInstenceService.audit(entity);
        return getR();
    }

    /**
     * 分页列表
     */
    @RequestMapping("/page")
    @RequiresPermissions("flow:actflowinstence:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        PageEntity page = actFlowInstenceService.queryPage(params);
        return getR().result(page);
    }
    /**
     * 我的申请
     */
    @RequestMapping("/mylist")
//    @RequiresPermissions("flow:actflowinstence:mylist")
    public ReturnEntity mylist(@RequestBody Map<String, Object> params){
        PageEntity page = actFlowInstenceService.queryPage(0, FlowTableNameEnum.work_leave_apply,params);
        return getR().result(page);
    }

    /**
     * 代办
     */
    @RequestMapping("/todolist")
//    @RequiresPermissions("flow:actflowinstence:todolist")
    public ReturnEntity todolist(@RequestBody Map<String, Object> params){
        PageEntity page = actFlowInstenceService.queryPage(1, FlowTableNameEnum.work_leave_apply,params);
        return getR().result(page);
    }
    /**
     * 已办
     */
    @RequestMapping("/historylist")
//    @RequiresPermissions("flow:actflowinstence:historylist")
    public ReturnEntity historylist(@RequestBody Map<String, Object> params){
        PageEntity page = actFlowInstenceService.queryPage(2, FlowTableNameEnum.work_leave_apply,params);
        return getR().result(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("flow:actflowinstence:info")
    public ReturnEntity info(@PathVariable("id") Integer id){
		ActFlowInstenceEntity actFlowInstence = actFlowInstenceService.getById(id);
        return getR().result(actFlowInstence);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("flow:actflowinstence:save")
    public ReturnEntity save(@RequestBody ActFlowInstenceEntity actFlowInstence){
		actFlowInstenceService.save(actFlowInstence);
        return getR();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("flow:actflowinstence:update")
    public ReturnEntity update(@RequestBody ActFlowInstenceEntity actFlowInstence){
		actFlowInstenceService.updateById(actFlowInstence);
        return getR();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("flow:actflowinstence:delete")
    public ReturnEntity delete(@RequestBody Integer[] ids){
		actFlowInstenceService.removeByIds(Arrays.asList(ids));
        return getR();
    }

}
