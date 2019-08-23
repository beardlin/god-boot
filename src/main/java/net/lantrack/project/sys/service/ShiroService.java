
package net.lantrack.project.sys.service;

import net.lantrack.project.sys.entity.SysUserEntity;
import net.lantrack.project.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 *@Description shiro
 *@Author lantrack
 *@Date 2019/8/23  14:24
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);
}
