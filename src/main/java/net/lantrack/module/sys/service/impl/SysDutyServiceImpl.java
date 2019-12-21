package net.lantrack.module.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.sys.dao.SysDutyDao;
import net.lantrack.module.sys.entity.SysDutyEntity;
import net.lantrack.module.sys.service.SysDutyService;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Service("sysDutyService")
public class SysDutyServiceImpl extends ServiceImpl<SysDutyDao, SysDutyEntity> implements SysDutyService {

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<SysDutyEntity> page = this.page(
                new Query<SysDutyEntity>().getPage(params),
                new QueryWrapper<SysDutyEntity>()
        );

        return new PageEntity(page);
    }

}
