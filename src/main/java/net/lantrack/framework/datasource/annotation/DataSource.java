
package net.lantrack.framework.datasource.annotation;

import java.lang.annotation.*;

/**
 *@Description 多数据源注解
 *@Author lantrack
 *@Date 2019/8/22  17:08
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}
