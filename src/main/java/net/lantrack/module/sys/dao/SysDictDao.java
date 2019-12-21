package net.lantrack.module.sys.dao;

import net.lantrack.module.sys.entity.SysDictEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {

    /**
     * 获取字典值
     * @param dictType
     * @param dictkey
     * @return
     */
    @Select("select d_val from sys_dict where d_type = #{dictType} and d_key = #{dictkey} ")
    String getDictValue(@Param("dictType") String dictType,@Param("dictkey") String dictkey);
}
