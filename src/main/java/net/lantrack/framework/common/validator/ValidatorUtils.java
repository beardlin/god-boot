
package net.lantrack.framework.common.validator;

import net.lantrack.framework.common.exception.GlobalException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 *@Description 校验工具类
 *@Author lantrack
 *@Date 2019/8/22  17:01
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws GlobalException  校验不通过，则报GlobalException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws GlobalException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage()).append("<br>");
            }
            throw new GlobalException(msg.toString());
        }
    }
}
