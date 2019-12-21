
package net.lantrack.module.sys.form;

import lombok.Data;

/**
 *@Description  密码表单
 *@Author dahuzi
 *@Date 2019/10/29  17:48
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;


}
