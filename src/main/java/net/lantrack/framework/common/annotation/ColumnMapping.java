package net.lantrack.framework.common.annotation;

import java.lang.annotation.*;


/**
 * 结果集到对象映射注解，加到属性上
 * 例：
 * @ColumnMapping("user_name")
 * private String uName;
 * @author lin
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnMapping {
    String value();
}
