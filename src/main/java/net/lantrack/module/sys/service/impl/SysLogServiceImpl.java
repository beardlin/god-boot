
package net.lantrack.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.module.sys.dao.SysLogDao;
import net.lantrack.module.sys.entity.SysLogEntity;
import net.lantrack.module.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *@Description  日志管理
 *@Author dahuzi
 *@Date 2019/10/29  17:55
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        String username = (String)params.get("username");
        String start = (String)params.get("startTime");
        String end = (String)params.get("endTime");
        String operation = (String)params.get("operation");

        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            new QueryWrapper<SysLogEntity>()
                .like(StringUtils.isNotBlank(username),"username", username)
                .like(StringUtils.isNotBlank(operation),"operation", operation)
                .gt(StringUtils.isNotBlank(start),"create_date",start)
                .lt(StringUtils.isNotBlank(end),"create_date",start)

        );

        return new PageEntity(page);
    }
}
