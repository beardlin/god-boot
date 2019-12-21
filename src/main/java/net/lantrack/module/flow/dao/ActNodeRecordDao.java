package net.lantrack.module.flow.dao;

import net.lantrack.module.flow.entity.ActNodeRecordEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *@Description 节点审批记录
 *@Author ldahuzi
 *@Date 2019-11-05 22:17:05
 */
@Mapper
public interface ActNodeRecordDao extends BaseMapper<ActNodeRecordEntity> {

}
