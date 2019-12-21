
package net.lantrack.module.sys.form;

import lombok.Data;

/**
 *@Description  登录表单
 *@Author dahuzi
 *@Date 2019/10/29  17:48
 */
@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;

}
