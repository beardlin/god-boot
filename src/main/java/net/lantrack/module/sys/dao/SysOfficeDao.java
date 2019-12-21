package net.lantrack.module.sys.dao;

import net.lantrack.module.sys.entity.SysOfficeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.module.sys.model.TreeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *@Description 组织机构表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Mapper
public interface SysOfficeDao extends BaseMapper<SysOfficeEntity> {

    @Update("update sys_office set o_status = #{value} where id = #{id}")
    void stop(@Param("id") Object id,@Param("value") Object value);

    /**
     * 递归查询所有层级
     * @param id
     * @return
     */
    TreeModel getTree(@Param("id") String id);

    /**
     * 查询当前部门下的子部门
     * @param id
     * @return
     */
    List<SysOfficeEntity> queryByPid(@Param("id") Long id);
}
