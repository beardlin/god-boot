
package net.lantrack.framework.datasource.annotation;

import java.lang.annotation.*;

/**
 *@Description  多数据源注解
 *@Author dahuzi
 *@Date 2019/10/28  22:05
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {
    String value() default "";
}
