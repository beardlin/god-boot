package net.lantrack.module.survey.em;

import lombok.Data;

/**
 *@Description  问卷模板状态
 *@Author dahuzi
 *@Date 2019/12/7  18:04
 */
public enum ThemeStatusEnum {
    DRAFT("0","草稿"),
    PUBLISH("1","已发布"),
    VOID("2","已作废");
    private String code;
    private String msg;
    ThemeStatusEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }}
