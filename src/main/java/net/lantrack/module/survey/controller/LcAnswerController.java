package net.lantrack.module.survey.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.utils.MongoDbUtils;
import net.lantrack.module.survey.entity.LcThemeEntity;
import net.lantrack.module.survey.model.QuestionAnalyze;
import net.lantrack.module.survey.service.LcThemeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lantrack.module.survey.entity.LcAnswerEntity;
import net.lantrack.module.survey.service.LcAnswerService;
import net.lantrack.framework.common.entity.ReturnEntity;



/**
 *@Description 答卷管理
 *@Author ldahuzi
 *@Date 2019-12-13 14:20:03
 */
@RestController
@RequestMapping("survey/lcanswer")
public class LcAnswerController extends BaseController{
    @Autowired
    private LcAnswerService lcAnswerService;
    @Autowired
    private LcThemeService lcThemeService;

    /**
     * 答卷列表
     * survey/lcanswer/page
     */
    @RequestMapping("/page")
//    @RequiresPermissions("survey:lcanswer:page")
    public ReturnEntity page(@RequestBody Map<String, Object> params){
        return getR().result(lcAnswerService.queryPage(params));
    }



    /**
     *答案统计
     * survey/lcanswer/analyze/{themeId}
     */
    @RequestMapping("/analyze/{themeId}")
//    @RequiresPermissions("survey:lcanswer:analyze")
    public ReturnEntity analyze(@PathVariable("themeId") String themeId){
        List<QuestionAnalyze> analyze = lcAnswerService.analyze(themeId);
        LcThemeEntity theme = lcThemeService.getById(themeId);
        return getR().put("theme",theme.getTableTitle()).put("analyze",analyze);
    }

    /**
     *答案基本信息
     * survey/lcanswer/info/{id}
     */
    @RequestMapping("/info/{id}")
//    @RequiresPermissions("survey:lcanswer:info")
    public ReturnEntity info(@PathVariable("id") String id){
		LcAnswerEntity lcAnswer = lcAnswerService.getById(id);
        return getR().result(lcAnswer);
    }

    /**
     * 答案详情
     * survey/lcanswer/detail/{id}
     */
    @RequestMapping("/detail/{id}")
//    @RequiresPermissions("survey:lcanswer:info")
    public ReturnEntity detail(@PathVariable("id") String id){
        return getR().result(lcAnswerService.getAnswerById(id));
    }

    /**
     * 保存
     * survey/lcanswer/save
     */
    @RequestMapping("/save")
//    @RequiresPermissions("survey:lcanswer:save")
    public ReturnEntity save(@RequestBody String json){
        this.lcAnswerService.saveJson(json);
        return getR();
    }

    /**
     * 答卷修改
     * survey/lcanswer/update
     */
    @RequestMapping("/update")
//    @RequiresPermissions("survey:lcanswer:update")
    public ReturnEntity update(@RequestBody String json){
		lcAnswerService.updateJson(json);
        return getR();
    }

    /**
     * 批量删除删除答卷
     * survey/lcanswer/delete
     */
    @RequestMapping("/delete")
//    @RequiresPermissions("survey:lcanswer:delete")
    public ReturnEntity delete(@RequestBody String[] ids){
		lcAnswerService.deleteByIds(Arrays.asList(ids));
        return getR();
    }

}
