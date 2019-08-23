package net.lantrack.framework.common.annotation;

import java.lang.annotation.*;

/**
 *@Description 系统日志注解
 *@Author lantrack
 *@Date 2019/8/22  16:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
