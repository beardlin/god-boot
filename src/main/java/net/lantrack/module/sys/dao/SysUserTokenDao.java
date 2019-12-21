
package net.lantrack.module.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.module.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 *@Description  用户Token
 *@Author dahuzi
 *@Date 2019/10/29  17:43
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    @Update("update sys_user_token set expire_time = #{expireTime},update_time = #{updateTime} where token = #{token}")
    void updateToken(@Param("token") String token,@Param("expireTime") Date expire,@Param("updateTime") Date update);

    SysUserTokenEntity queryByToken(String token);

}
