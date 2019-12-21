package net.lantrack.module.flow.model;

import net.lantrack.module.flow.enums.FlowCodeTypeEnum;
import lombok.Data;

/**
 * 用于解析规则内容
 */
@Data
public class FlowCode {
    private FlowCodeTypeEnum codeType;
    private String codeValue;

    public FlowCode() {
    }

    public FlowCode(FlowCodeTypeEnum codeType, String codeValue) {
        this.codeType = codeType;
        this.codeValue = codeValue;
    }
}
