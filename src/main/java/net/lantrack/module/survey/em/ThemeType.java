package net.lantrack.module.survey.em;

/**
 * 模板类型
 */
public enum ThemeType {
    SURVEY("survey"),
    EXAM("exam");
    private String code;
    ThemeType(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }}
