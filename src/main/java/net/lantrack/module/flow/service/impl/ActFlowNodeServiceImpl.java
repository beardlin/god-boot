package net.lantrack.module.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.module.flow.dao.ActFlowNodeDao;
import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import net.lantrack.module.flow.service.ActFlowNodeService;
import net.lantrack.module.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description 审批节点表
 * @Author ldahuzi
 * @Date 2019-11-05 22:17:05
 */
@Service("actFlowNodeService")
public class ActFlowNodeServiceImpl extends ServiceImpl<ActFlowNodeDao, ActFlowNodeEntity> implements ActFlowNodeService {

    @Autowired
    SysUserService sysUserService;


    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<ActFlowNodeEntity> page = this.page(
                new Query<ActFlowNodeEntity>().getPage(params),
                new QueryWrapper<ActFlowNodeEntity>()
        );

        return new PageEntity(page);
    }

    @Override
    public List<ActFlowNodeEntity> queryAllByFlowId(Integer flowId) {
        List<ActFlowNodeEntity> list = this.list(new QueryWrapper<ActFlowNodeEntity>().eq("flow_id",
                flowId).orderByAsc("node_sort"));
        return list;
    }

    // 判断跨部门是否为1，看部门id与职务id的人存在不
    public Boolean checkDuty(ActFlowNodeEntity actFlowNode) {
        if (actFlowNode.getApproverType() == 1) {
            return true;
        }
        if (actFlowNode.getCrosOffcie() == 0) {
            return true;
        }
        Integer integer = sysUserService.queryDutyCount(actFlowNode.getApproverOffice(), actFlowNode.getApproverId());
        sysUserService.queryMasterId(actFlowNode.getApproverOffice());
        if (integer>0) {
            return true;
        }
        return false;
    }


}
