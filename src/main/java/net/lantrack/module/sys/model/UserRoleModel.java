package net.lantrack.module.sys.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description 角色下的用户列表
 * @Author dahuzi
 * @Date 2019/11/19 23:00
 */
@Data
public class UserRoleModel implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 当前角色id
     */
    private Long roleId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 所属机构
     */
    private String officeName;
    /**
     * 职务
     */
    private String dutyName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 电话
     */
    private String phone;
    /**
     * 角色
     */
    private String roleNames;
}
