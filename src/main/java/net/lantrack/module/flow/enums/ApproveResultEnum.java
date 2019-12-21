package net.lantrack.module.flow.enums;
/**
 *@Description 审批结果
 *@Author dahuzi
 *@Date 2019/12/5  14:34
 */
public enum ApproveResultEnum {
    PENDING(0, "待处理"),
    PASS(1, "通过"),
    NO_PASS(2, "不通过"),
    STOP(3, "终止");

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

    ApproveResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ApproveResultEnum valueOf(Integer code){
        ApproveResultEnum[] values = ApproveResultEnum.values();
        for (int i = 0; i < values.length; i++) {
            ApproveResultEnum value = values[i];
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;

    }

}
