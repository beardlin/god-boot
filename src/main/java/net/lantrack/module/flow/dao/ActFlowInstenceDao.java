package net.lantrack.module.flow.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import net.lantrack.module.flow.entity.ActFlowInstenceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *@Description 流程实例表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Mapper
public interface ActFlowInstenceDao extends BaseMapper<ActFlowInstenceEntity> {

    Integer selectMaxCodeAotoByFlowId(Integer flowId);

    IPage<ActFlowInstenceEntity> selectHistoryFlowPage(IPage<Map<String,Object>> page,
                                                              @Param("req") Map<String, Object> params);
    IPage<ActFlowInstenceEntity> selectTodoFlowPage(IPage<Map<String,Object>> page,
                                                              @Param("req") Map<String, Object> params);

}
