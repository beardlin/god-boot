
package net.lantrack.framework.common.validator;

import net.lantrack.framework.common.exception.GlobalException;
import org.apache.commons.lang.StringUtils;

/**
 *@Description 数据校验
 *@Author lantrack
 *@Date 2019/8/22  17:00
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new GlobalException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new GlobalException(message);
        }
    }
}
