package net.lantrack.module.survey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.survey.entity.LcThemeEntity;

import java.util.Map;

/**
 *@Description 问卷模板
 *@Author ldahuzi
 *@Date 2019-12-12 10:16:41
 */
public interface LcThemeService extends IService<LcThemeEntity> {

    /**
     * 修改答题限制
     * @param params
     */
    void updateLimit(Map<String, Object> params);

    /**
     * 保存模板
     * @param json
     */
    void saveTheme(String json);
    /**
     * 修改模板
     * @param json
     */
    void updateTheme(String json);

    PageEntity queryPage(Map<String, Object> params);
}

