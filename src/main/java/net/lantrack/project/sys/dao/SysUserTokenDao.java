
package net.lantrack.project.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *@Description 用户Token
 *@Author lantrack
 *@Date 2019/8/23  10:21
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
