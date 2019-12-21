
package net.lantrack.framework.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.lantrack.framework.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 *@Description 分页查询
 *@Author dahuzi
 *@Date 2019/10/28  21:46
 */
public class Query<T> {

    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        //分页参数
        long curPage = 1;
        long limit = 10;
        try{
            if(params.get(Constant.PAGE) != null){
                curPage = Long.parseLong(obj2Str(params.get(Constant.PAGE)));
            }
            if(params.get(Constant.LIMIT) != null){
                limit = Long.parseLong(obj2Str(params.get(Constant.LIMIT)));
            }
        }catch (Exception e){

        }
        //分页对象
        Page<T> page = new Page<>(curPage, limit);
        //分页参数
        params.put(Constant.PAGE, page);

        //排序字段
        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String orderField = SQLFilter.sqlInject(obj2Str(params.get(Constant.ORDER_FIELD)));
        String order = obj2Str(params.get(Constant.ORDER));
        //前端字段排序
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(Constant.ASC.equalsIgnoreCase(order)) {
                return page.setAsc(orderField);
            }else {
                return page.setDesc(orderField);
            }
        }

        //默认排序
        if(isAsc) {
            page.setAsc(defaultOrderField);
        }else {
            page.setDesc(defaultOrderField);
        }

        return page;
    }

    String obj2Str(Object obj){
        return obj == null?"":obj.toString();
    }

}
