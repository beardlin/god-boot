package net.lantrack.module.flow.enums;

public enum ApproverTypeEnum {
    /**
     * 审批人类型（1人员2职务）,如果当前部门下没有该角色人员则默认部门负责人审批
     */

    PERSONNEL(1, "指定人员"),
    DUTY(2, "指定职务");

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

    ApproverTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
