package net.lantrack.module.flow.service.impl;

import net.lantrack.module.flow.entity.ActFlowNodeEntity;
import net.lantrack.module.flow.model.ActFlowInfoResp;
import net.lantrack.module.flow.service.ActFlowNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.flow.dao.ActFlowDao;
import net.lantrack.module.flow.entity.ActFlowEntity;
import net.lantrack.module.flow.service.ActFlowService;

/**
 *@Description 流程定义表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Service("actFlowService")
public class ActFlowServiceImpl extends ServiceImpl<ActFlowDao, ActFlowEntity> implements ActFlowService {

    @Autowired
    private ActFlowNodeService actFlowNodeService;

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<ActFlowEntity> page = this.page(
                new Query<ActFlowEntity>().getPage(params),
                new QueryWrapper<ActFlowEntity>()
        );

        return new PageEntity(page);
    }

    @Override
    public ActFlowInfoResp info(int id) {
        ActFlowEntity actFlowEntity = this.getBaseMapper().selectById(id);
        List<ActFlowNodeEntity> list = actFlowNodeService.queryAllByFlowId(id);
        ActFlowInfoResp actFlowInfoResp = new ActFlowInfoResp();
        actFlowInfoResp.setNodes(list);
        actFlowInfoResp.setFlow(actFlowEntity);
        return actFlowInfoResp;
    }
    @Override
    public ActFlowEntity getOneByTableName(String tableName) {
        ActFlowEntity actFlowEntity = this.getOne(new QueryWrapper<ActFlowEntity>().eq(
                "table_name",
                tableName));
        return actFlowEntity;
    }

}
