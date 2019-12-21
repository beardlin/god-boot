package net.lantrack.module.survey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.survey.entity.LcAnswerEntity;
import net.lantrack.module.survey.model.QuestionAnalyze;
import org.bson.Document;

import java.util.List;
import java.util.Map;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-12-13 14:20:03
 */
public interface LcAnswerService extends IService<LcAnswerEntity> {

    /**
     * 批量删除答卷
     * @param idList
     */
    void deleteByIds(List<String> idList);

    /**
     * 对当前模板进行统计
     * @param themeId
     */
    List<QuestionAnalyze> analyze(String themeId);

    /**
     * 获取答案详情
     * @param answerId
     * @return
     */
    Document getAnswerById(String answerId);

    /**
     * 提交答案
     * @param json
     */
    void saveJson(String json);
    /**
     * 修改答案
     * @param json
     */
    void updateJson(String json);

    PageEntity queryPage(Map<String, Object> params);
}

