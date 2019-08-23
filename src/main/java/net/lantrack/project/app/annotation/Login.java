
package net.lantrack.project.app.annotation;

import java.lang.annotation.*;

/**
 *@Description app登录效验
 *@Author lantrack
 *@Date 2019/8/22  18:03
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
