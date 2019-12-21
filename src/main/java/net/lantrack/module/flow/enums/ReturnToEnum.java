package net.lantrack.module.flow.enums;

public enum ReturnToEnum {
    FIRST(0, "发起人"),
    PREV(1, "上一级节点"),
    PREV_ANY(2, "之前任意节点");

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

    ReturnToEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static   ReturnToEnum valueOf(Integer code){
        ReturnToEnum[] values = ReturnToEnum.values();
        for (int i = 0; i < values.length; i++) {
            ReturnToEnum value = values[i];
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

}
