package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysDutyEntity;

import java.util.Map;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
public interface SysDutyService extends IService<SysDutyEntity> {

    PageEntity queryPage(Map<String, Object> params);
}

