package net.lantrack.framework.common.component;

import net.lantrack.framework.common.entity.ReturnEntity;

/**
 *@Description 基础Controller
 *@Author lantrack
 *@Date 2019/9/11  15:26
 */
public abstract class BaseController {

    private static final ReturnEntity r = new ReturnEntity();

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
