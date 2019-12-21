package net.lantrack.module.survey.dao;

import net.lantrack.module.survey.entity.LcAnswerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-12-13 14:20:03
 */
@Mapper
public interface LcAnswerDao extends BaseMapper<LcAnswerEntity> {

    /**
     * 保存答案
     * @param entity
     */
    void saveAnswer(@Param("result") LcAnswerEntity entity);
}
