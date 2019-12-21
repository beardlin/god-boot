package net.lantrack.module.flow.dao;

import net.lantrack.module.flow.entity.ActFlowEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *@Description 流程定义表
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Mapper
public interface ActFlowDao extends BaseMapper<ActFlowEntity> {

}
