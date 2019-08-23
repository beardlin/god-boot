
package net.lantrack.framework.common.validator.group;

import javax.validation.GroupSequence;

/**
 *@Description 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *@Author lantrack
 *@Date 2019/8/22  16:59
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
