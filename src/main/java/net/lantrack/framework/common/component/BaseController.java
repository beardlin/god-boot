package net.lantrack.framework.common.component;

import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.validator.ValidatorUtils;
import com.google.gson.Gson;


/**
 *@Description 基础Controlller
 *@Author dahuzi
 *@Date 2019/10/28  21:31
 */
public abstract class BaseController {

    private static final ReturnEntity r = new ReturnEntity();


    protected <T> T jsonToObject(String json,Class<T> clazz){
        return new Gson().fromJson(json, clazz);
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws GlobalException  校验不通过，则报GlobalException异常
     */
    public static void validate(Object object, Class<?>... groups)
            throws GlobalException {
        ValidatorUtils.validateEntity(object,groups);
    }

    /**
     * 获取返回对象
     * @return
     */
    public static ReturnEntity getR() {
        try{
           return  (ReturnEntity) r.clone();
        }catch (Exception e){
            return new ReturnEntity();
        }
    }
}
