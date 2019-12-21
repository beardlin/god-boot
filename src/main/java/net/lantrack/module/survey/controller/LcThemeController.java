package net.lantrack.module.survey.controller;

import java.util.Arrays;
import java.util.Map;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.utils.MongoDbUtils;
import net.lantrack.module.survey.em.ThemeType;
import net.lantrack.module.survey.entity.LcThemeEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.lantrack.module.survey.service.LcThemeService;
import net.lantrack.framework.common.entity.ReturnEntity;



/**
 *@Description 问卷模板
 *@Author ldahuzi
 *@Date 2019-12-12 10:16:41
 */
@RestController
@RequestMapping("/survey/theme")
public class LcThemeController extends BaseController {
    @Autowired
    private LcThemeService lcThemeService;

    @Autowired
    private MongoDbUtils mongoDbUtils;


    /**
     * 校验答题码
     * survey/theme/validcode
     */
    @RequestMapping("/validcode")
//    @RequiresPermissions("survey:lctheme:page")
    public ReturnEntity validcode(@RequestBody Map<String,String> parms){
        String id = parms.get("id");
        String code = parms.get("code");
        if(StringUtils.isBlank(id)||StringUtils.isBlank(code)){
            getR().err("请求参数异常");
        }
        LcThemeEntity theme = this.lcThemeService.getById(id);
        if(!code.equals(theme.getAnswerPwd())){
            return getR().err("校验码错误");
        }
        return getR();
    }

    /**
     * 修改答题限制
     * survey/theme/limit
     */
    @RequestMapping("/limit")
//    @RequiresPermissions("survey:lctheme:page")
    public ReturnEntity limit(@RequestBody Map<String, Object> params){
        lcThemeService.updateLimit(params);
        return getR();
    }

    /**
     * 列表
     * survey/theme/page
     */
    @RequestMapping("/page")
//    @RequiresPermissions("survey:lctheme:page")
    public ReturnEntity list(@RequestBody Map<String, Object> params){
        return getR().result(lcThemeService.queryPage(params));
    }

    /**
     * 获取模板基本信息
     * survey/theme/detail/{id}
     */
    @RequestMapping("/detail/{id}")
    public ReturnEntity detail(@PathVariable("id") String id){
        return getR().result(this.lcThemeService.getById(id));
    }

    /**
     * 获取模板详情
     * survey/theme/info/{id}
     */
    @RequestMapping("/info/{id}")
    public ReturnEntity info(@PathVariable("id") String id){
        Document doc = mongoDbUtils.findById(id, ThemeType.SURVEY.getCode());
        doc.remove("_id");
        return getR().result(doc);
    }

    /**
     * 保存草稿
     * survey/theme/save
     */
    @RequestMapping("/save")
//    @RequiresPermissions("survey:lctheme:save")
    public ReturnEntity save(@RequestBody String json){
        this.lcThemeService.saveTheme(json);
        return getR();
    }

    /**
     * 修改模板
     * survey/theme/update
     */
    @RequestMapping("/update")
//    @RequiresPermissions("survey:lctheme:update")
    public ReturnEntity update(@RequestBody String json){
        this.lcThemeService.updateTheme(json);
        return getR();
    }

    /**
     * 删除
     * survey/theme/delete/{id}
     */
    @RequestMapping("/delete/{id}")
//    @RequiresPermissions("survey:lctheme:delete")
    public ReturnEntity delete(@PathVariable("id") String id){
        if(StringUtils.isNotBlank(id)){
            lcThemeService.removeById(id);
            Document doc = mongoDbUtils.findById(id, ThemeType.SURVEY.getCode());
            mongoDbUtils.remove(doc);
        }
        return getR();
    }

}
