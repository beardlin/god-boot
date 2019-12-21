package net.lantrack.module.flow.enums;

public enum FlowCodeTypeEnum {

    constant("常量"),
    date("日期"),
    inc("自增");

    private String rule;

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    FlowCodeTypeEnum(String rule){
    }



}
