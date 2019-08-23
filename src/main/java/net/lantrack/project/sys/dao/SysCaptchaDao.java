
package net.lantrack.project.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.lantrack.project.sys.entity.SysCaptchaEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *@Description 验证码
 *@Author lantrack
 *@Date 2019/8/23  10:25
 */
@Mapper
public interface SysCaptchaDao extends BaseMapper<SysCaptchaEntity> {

}
