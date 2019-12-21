package net.lantrack.module.survey.service.impl;

import net.lantrack.framework.common.utils.MongoDbUtils;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.survey.em.ThemeType;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.survey.dao.LcThemeDao;
import net.lantrack.module.survey.entity.LcThemeEntity;
import net.lantrack.module.survey.service.LcThemeService;

/**
 *@Description 问卷模板
 *@Author ldahuzi
 *@Date 2019-12-12 10:16:41
 */
@Service("lcThemeService")
public class LcThemeServiceImpl extends ServiceImpl<LcThemeDao, LcThemeEntity> implements LcThemeService {

    @Autowired
    private MongoDbUtils mongoDbUtils;

    @Override
    public void updateLimit(Map<String, Object> params) {
        Object id = params.get("id");
        Object limit = params.get("limit");
        Object password = params.get("password");
        this.baseMapper.updateLimit(id,limit,password);
    }

    @Override
    public void saveTheme(String json) {
        Document doc = mongoDbUtils.saveJson(json, ThemeType.SURVEY.getCode());
        //获取调查模板基本信息
        LcThemeEntity theme = docConversTheme(doc);
        theme.setCreateBy(UserUtils.getUserName());
        theme.setCreateDate(new Date());
        theme.setAnswerNum(0);
        theme.setAnswerLimit(0);
        theme.setAnswerPwd("123456");
        try{
            this.baseMapper.insertTheme(theme);
        }catch (Exception e){
            mongoDbUtils.remove(doc);
        }
    }
    LcThemeEntity docConversTheme(Document doc){
        //获取调查模板基本信息
        Object id = doc.get("_id");
        Document themeDoc = doc.get("theme", Document.class);
        Object themeId = themeDoc.get("id");
        Object tableTitle = themeDoc.get("tableTitle");
        Object tableExplain = themeDoc.get("tableExplain");
        Object tableCode = themeDoc.get("tableCode");
        Object tableStatus = themeDoc.get("tableStatus");
        Object hiddenCode = themeDoc.get("hiddenCode");
        LcThemeEntity theme = new LcThemeEntity();
        theme.setUpdateBy(UserUtils.getUserName());
        theme.setUpdateDate(new Date());
        //整个文档的mongoDB的id
        theme.setId(objToStr(id));
        theme.setThemeId(objToStr(themeId));
        theme.setTableTitle(objToStr(tableTitle));
        theme.setTableExplain(objToStr(tableExplain));
        theme.setTableCode(objToStr(tableCode));
        theme.setTableStatus(objToStr(tableStatus));
        theme.setHiddenCode(objToStr(hiddenCode));
        return  theme;
    }
    @Override
    public void updateTheme(String json) {
        Document doc = Document.parse(json);
        Document themeDoc = doc.get("theme", Document.class);
        Object themeId = themeDoc.get("id");
        LcThemeEntity th = this.baseMapper.selectOne(
                new QueryWrapper<LcThemeEntity>().eq("theme_id",themeId)
        );
        Document ori = mongoDbUtils.findById(th.getId(), ThemeType.SURVEY.getCode());
        doc.append("_id",ori.get("_id"));
        mongoDbUtils.saveOrUpdate(doc,ThemeType.SURVEY.getCode());
        LcThemeEntity theme = docConversTheme(doc);
        LcThemeEntity byId = this.getById(theme.getId());
        theme.setCreateDate(byId.getCreateDate());
        theme.setCreateBy(byId.getCreateBy());
        this.updateById(theme);
    }

    String objToStr(Object obj){
        return  obj == null?"":obj.toString();
    }

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<LcThemeEntity> page = this.page(
                new Query<LcThemeEntity>().getPage(params),
                new QueryWrapper<LcThemeEntity>()
                    .eq(StringUtils.isNotBlank(objToStr(params.get("status"))),"table_status",params.get("status"))
                    .orderByDesc("create_date")
        );
        return new PageEntity(page);
    }

}
