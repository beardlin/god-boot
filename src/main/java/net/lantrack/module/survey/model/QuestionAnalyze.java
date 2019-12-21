package net.lantrack.module.survey.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class QuestionAnalyze implements Serializable {

    private String qId;//问题id
    private String title;//问题名称
    private List<OptionAnalyze> options;//当前问题下选项

}
