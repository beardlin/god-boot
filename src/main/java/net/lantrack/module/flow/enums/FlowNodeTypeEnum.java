package net.lantrack.module.flow.enums;

public enum FlowNodeTypeEnum {
    COMMON(0, "普通"),
    OR(1, "或签"),
    AND(2, "并签"),
    START(8, "开始"),
    END(9, "结束");
    private Integer code;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    FlowNodeTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static FlowNodeTypeEnum valueOf(Integer code){
        FlowNodeTypeEnum[] values = FlowNodeTypeEnum.values();
        for (int i = 0; i < values.length; i++) {
            FlowNodeTypeEnum value = values[i];
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

}
