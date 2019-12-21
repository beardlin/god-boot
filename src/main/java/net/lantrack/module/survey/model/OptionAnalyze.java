package net.lantrack.module.survey.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class OptionAnalyze implements Serializable {
    private String oId;//选项id
    private String oTitle;//选项标题
    private Integer count;//选中数量
}
