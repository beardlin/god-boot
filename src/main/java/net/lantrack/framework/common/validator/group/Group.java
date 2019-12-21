
package net.lantrack.framework.common.validator.group;

import javax.validation.GroupSequence;

/**
 *@Description  定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *@Author dahuzi
 *@Date 2019/10/28  21:56
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
