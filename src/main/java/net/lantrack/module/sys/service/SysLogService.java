
package net.lantrack.module.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysLogEntity;

import java.util.Map;

/**
 *@Description  系统日志
 *@Author dahuzi
 *@Date 2019/10/29  17:53
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageEntity queryPage(Map<String, Object> params);

}
