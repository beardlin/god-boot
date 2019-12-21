package net.lantrack.module.flow.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import net.lantrack.framework.common.utils.DateUtil;
import net.lantrack.module.flow.enums.FlowCodeTypeEnum;
import net.lantrack.module.flow.model.FlowCode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CodeRuleUtils {
    public static String getCodeRuleStrByCodeRule(String flowCode) {
        StringBuffer stringBuffer = new StringBuffer();
        List<FlowCode> listFlowCode = JSONArray.parseArray(flowCode, FlowCode.class);
        for (Iterator<FlowCode> iterator = listFlowCode.iterator(); iterator.hasNext(); ) {
            FlowCode next = iterator.next();
            FlowCodeTypeEnum codeType = next.getCodeType();
            switch (codeType) {
                case constant:
                    stringBuffer.append(next.getCodeValue());
                    break;
                case date:
                    String date = DateUtil.getDate(next.getCodeValue());
                    stringBuffer.append(date);
                    break;
            }
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        List<FlowCode> list=  new ArrayList<>();
        list.add(new FlowCode(FlowCodeTypeEnum.constant,"meeting"));
        list.add(new FlowCode(FlowCodeTypeEnum.date,"yyyyMMdd"));
        String data = JSON.toJSONString(list);
        System.out.println(data);
        System.out.println(getCodeRuleStrByCodeRule(data));
    }
}
