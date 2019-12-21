package net.lantrack.module.flow.enums;

public enum FlowInstanceStatusEnum {

    DRAFT(0, "草稿"),
    PROCESSED(1, "审批中"),
    END(2, "结束"),
    RECALL(3, "撤回");

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

    FlowInstanceStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public static   FlowInstanceStatusEnum valueOf(Integer code){
        FlowInstanceStatusEnum[] values = FlowInstanceStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            FlowInstanceStatusEnum value = values[i];
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
