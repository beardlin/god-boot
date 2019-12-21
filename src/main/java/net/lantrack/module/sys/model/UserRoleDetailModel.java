package net.lantrack.module.sys.model;

import lombok.Data;

import java.io.Serializable;
/**
 *@Description  用户角色详情
 *@Author dahuzi
 *@Date 2019/11/29  13:38
 */
@Data
public class UserRoleDetailModel implements Serializable {

    private Long userId;
    private Long officeId;
    private String rolesId;
}
