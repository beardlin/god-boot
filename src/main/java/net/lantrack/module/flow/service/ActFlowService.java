package net.lantrack.module.flow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.flow.entity.ActFlowEntity;
import net.lantrack.module.flow.model.ActFlowInfoResp;

import java.util.Map;

/**
 *@Description 流程定义表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
public interface ActFlowService extends IService<ActFlowEntity> {

    PageEntity queryPage(Map<String, Object> params);

    ActFlowInfoResp info(int id);

    ActFlowEntity getOneByTableName(String tableName);
}

