
package net.lantrack.module.sys.service;

import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 *@Description  shiro
 *@Author dahuzi
 *@Date 2019/10/29  17:53
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
