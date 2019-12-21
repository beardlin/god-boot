package net.lantrack.module.survey.service.impl;

import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.MongoDbUtils;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.survey.em.QuestionTypeEnum;
import net.lantrack.module.survey.em.ThemeType;
import net.lantrack.module.survey.entity.LcThemeEntity;
import net.lantrack.module.survey.model.OptionAnalyze;
import net.lantrack.module.survey.model.QuestionAnalyze;
import net.lantrack.module.survey.service.LcThemeService;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.survey.dao.LcAnswerDao;
import net.lantrack.module.survey.entity.LcAnswerEntity;
import net.lantrack.module.survey.service.LcAnswerService;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-12-13 14:20:03
 */
@Service("lcAnswerService")
public class LcAnswerServiceImpl extends ServiceImpl<LcAnswerDao, LcAnswerEntity> implements LcAnswerService {

    public final static String ANSWER_COLL_PREFIX ="theme_";

    @Autowired
    MongoDbUtils mongoDbUtils;

    @Autowired
    LcThemeService lcThemeService;

    @Override
    public void deleteByIds(List<String> idList) {
        LcAnswerEntity answer = this.getById(idList.get(0));
        String themeId = answer.getThemeId();
        if(answer==null){
            throw  new GlobalException("数据不存在，请刷新后在尝试");
        }
        this.removeByIds(idList);
        for(String id:idList){
            Document doc = this.mongoDbUtils.findById(id, ANSWER_COLL_PREFIX + themeId);
            this.mongoDbUtils.remove(doc);
        }
        LcThemeEntity theme = lcThemeService.getById(themeId);
        int num = theme.getAnswerNum() - idList.size();
        if(num>0){
            theme.setAnswerNum(num);
            this.lcThemeService.updateById(theme);
        }

    }

    @Override
    public  List<QuestionAnalyze> analyze(String themeId) {
        Document theme = mongoDbUtils.findById(themeId, ThemeType.SURVEY.getCode());
        //根据模板加载出基础结构
        Map<String,String> questionsMapping = new HashMap<>();
        Map<String,String> optionsMapping = new HashMap<>();
        Map<String, Set<String>> frameMap = getFrameMap(theme,questionsMapping,optionsMapping);
        //统计每个答案的数量
        Map<String, Integer> answerNumMap = getAnswerNumMap(themeId);
        List<QuestionAnalyze> analyzeList = new ArrayList<>(frameMap.size());
        for(Map.Entry<String,Set<String>> entry:frameMap.entrySet()){
            QuestionAnalyze quesAn = new QuestionAnalyze();
            quesAn.setQId(entry.getKey());
            quesAn.setTitle(questionsMapping.get(entry.getKey()));
            analyzeList.add(quesAn);
            Set<String> optionIds = entry.getValue();
            List<OptionAnalyze> optionAnalyzeList = new ArrayList<>(optionIds.size());
            for(String oId:optionIds){
                OptionAnalyze opAn = new OptionAnalyze();
                opAn.setOId(oId);
                opAn.setOTitle(optionsMapping.get(oId));
                opAn.setCount(answerNumMap.get(oId));
                optionAnalyzeList.add(opAn);
            }
            quesAn.setOptions(optionAnalyzeList);
        }
        return  analyzeList;
    }
    //获取答案选项数量
    Map<String,Integer> getAnswerNumMap(String themeId){
        List<LcAnswerEntity> answerList = this.baseMapper.selectList(
                new QueryWrapper<LcAnswerEntity>().eq("theme_id", themeId)
        );
        Map<String,Integer> answerMap = new HashMap<>();
        for(LcAnswerEntity answer:answerList){
            Document answerDoc = mongoDbUtils.findById(answer.getId(), ANSWER_COLL_PREFIX + themeId);
            if(answerDoc==null){
                continue;
            }
            List<Document> list = (List<Document>) answerDoc.get("list");
            for(Document doc:list){
                Object optionId = doc.get("optionId");
                if(answerMap.containsKey(optionId)){
                    Integer num = answerMap.get(optionId);
                    answerMap.replace(optionId.toString(),num+1);
                }else {
                    answerMap.put(optionId.toString(),1);
                }
            }
        }
        return  answerMap;
    }
    //获取统计的结构
    Map<String,Set<String>> getFrameMap(Document theme,
                                                Map<String,String> quesMapping,Map<String,String> optionMapping){
        Map<String,Set<String>> quesAnalyze = new LinkedHashMap<>();
        List<Document> ques = (List<Document>)theme.get("questions");
        for(Document q:ques){
            Object questionType = q.get("questionType");
            if(!QuestionTypeEnum.RADIO.getCode().equals(questionType)
                    &&!QuestionTypeEnum.CHECKBOX.getCode().equals(questionType)){
                continue;
            }
            Object qId = q.get("id");
            Object title = q.get("title");
            quesMapping.put(qId.toString(),title.toString());
            List<Document> optionDoc = (List<Document>)q.get("optionJson");
            if(optionDoc.isEmpty()){
                continue;
            }
            Set<String> optionSet = new LinkedHashSet<>();
            for(Document option:optionDoc){
                Object oId = option.get("id");
                Object oTitle = option.get("oTitle");
                optionMapping.put(oId.toString(),oTitle.toString());
                optionSet.add(oId.toString());
            }
            quesAnalyze.put(qId.toString(),optionSet);
        }
        return quesAnalyze;
    }

    @Override
    public Document getAnswerById(String answerId) {
        LcAnswerEntity entity = this.getById(answerId);
        String themeId = entity.getThemeId();
        Document doc = mongoDbUtils.findById(answerId, ANSWER_COLL_PREFIX + themeId);
        doc.remove("_id");
        Document base = (Document) doc.get("base");
        base.replace("id",answerId);
        return doc;
    }

    @Override
    public void updateJson(String json) {
        Document update = Document.parse(json);
        Document base = (Document)update.get("base");
        //获取答案id
        Object ansId = base.get("id");
        if(ansId==null||"".equals(ansId)){
            throw  new GlobalException("数据不存在");
        }
        LcAnswerEntity answer = this.getById(ansId.toString());
        answer.setAnsTime(Long.valueOf(obj2Str(base.get("ansTime"))));
        answer.setAnsStatus(Integer.valueOf(obj2Str(base.get("ansStatus"))));
        Document ori = mongoDbUtils.findById(ansId.toString(), ANSWER_COLL_PREFIX + answer.getThemeId());
        update.append("_id",ori.get("_id"));
        mongoDbUtils.saveOrUpdate(update,ANSWER_COLL_PREFIX + answer.getThemeId());
        this.updateById(answer);
    }

    @Override
    public void saveJson(String json) {

        Document doc = Document.parse(json);
        Document base = (Document)doc.get("base");
        Object themeId = base.get("themeId");
        //检查是否超过限制答卷数量
        LcThemeEntity theme = lcThemeService.getById(themeId.toString());
        Integer answerNum = theme.getAnswerNum();
        if(answerNum!=null && answerNum>theme.getAnswerLimit()){
            throw new GlobalException("答卷收集结束");
        }
        //将当前答卷放在当前问卷模板集合中
        mongoDbUtils.saveOrUpdate(doc,ANSWER_COLL_PREFIX+themeId);
        LcAnswerEntity answer = docConvertAnswer(base);
        answer.setId(doc.get("_id").toString());
        if(StringUtils.isNotBlank(answer.getId())){
            answer.setCreateBy(UserUtils.getUserName());
            answer.setCreateDate(new Date());
            this.baseMapper.saveAnswer(answer);
        }
        //在问卷模板中将答卷数量+1
        theme.setAnswerNum(answerNum+1);
        this.lcThemeService.updateById(theme);
    }

    //将答案转换为对象
    private  LcAnswerEntity docConvertAnswer(Document doc){
        LcAnswerEntity answer = new LcAnswerEntity();
        answer.setAnsStatus(Integer.valueOf(obj2Str(doc.get("ansStatus"))));
        answer.setAnsTime(Long.valueOf(obj2Str(doc.get("ansTime"))));
        answer.setThemeId(obj2Str(doc.get("themeId")));
        return answer;
    }

    String obj2Str(Object obj){
        return  obj == null?"":obj.toString();
    }

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        Object themeId = params.get("themeId");
        IPage<LcAnswerEntity> page = this.page(
                new Query<LcAnswerEntity>().getPage(params),
                new QueryWrapper<LcAnswerEntity>()
                    .eq(themeId!=null&&!"".equals(themeId),"theme_id",themeId)
        );
        return new PageEntity(page);
    }

}
