package net.lantrack.module.flow.enums;

public enum FlowTableNameEnum {

    work_leave_apply("work_leave_apply","请假申请");

    private String tableName;
    private String comment;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    FlowTableNameEnum(String tableName, String comment){
        this.tableName=tableName;
        this.comment=comment;
    }

}
