package net.lantrack.framework.common.annotation;

import java.lang.annotation.*;

/**
 *@Description 自定义日志注解
 *@Author dahuzi
 *@Date 2019/10/28  21:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
