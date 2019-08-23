
package net.lantrack.project.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.app.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *@Description UserDao
 *@Author lantrack
 *@Date 2019/8/22  17:55
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
