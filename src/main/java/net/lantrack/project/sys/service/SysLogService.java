
package net.lantrack.project.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.utils.PageUtils;
import net.lantrack.project.sys.entity.SysLogEntity;

import java.util.Map;


/**
 *@Description 系统日志
 *@Author lantrack
 *@Date 2019/8/23  14:23
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
