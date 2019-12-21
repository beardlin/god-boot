package net.lantrack.module.survey.dao;

import net.lantrack.module.survey.entity.LcThemeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 *@Description 问卷模板
 *@Author ldahuzi
 *@Date 2019-12-12 10:16:41
 */
@Mapper
public interface LcThemeDao extends BaseMapper<LcThemeEntity> {

    /**
     * 修改答题份数
     */
    @Update("update lc_theme set answer_limit = #{limit},answer_pwd = #{pwd} where id = #{id}")
    void updateLimit(@Param("id") Object id, @Param("limit") Object limit, @Param("pwd") Object pwd);

    /**
     * 插入模板
     * @param entity
     */
    void insertTheme(@Param("result") LcThemeEntity entity);
}
