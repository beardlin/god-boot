package net.lantrack.module.sys.dao;

import net.lantrack.module.sys.entity.SysFileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *@Description 系统附件表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Mapper
public interface SysFileDao extends BaseMapper<SysFileEntity> {
    /**
     * 将文件从垃圾状态改为持久化
     * @param fileIds
     */
    void persistFile(@Param("fileIds") List<Long> fileIds);
}
