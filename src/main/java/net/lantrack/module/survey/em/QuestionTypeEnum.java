package net.lantrack.module.survey.em;

/**
 * 题型
 */
public enum QuestionTypeEnum {

    RADIO("radio"),//单选
    CHECKBOX("checkbox"),//多选
    INPUT("input"),//单项填空
    INPUT_MULTI("inputMulti"),//多项填空
    PARAGRAPH("paragraph"),//段落
    PAGE("page");//分页
    private String code;
    QuestionTypeEnum(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }}
