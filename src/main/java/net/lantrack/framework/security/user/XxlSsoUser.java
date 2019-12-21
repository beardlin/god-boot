package net.lantrack.framework.security.user;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author dahuzi
 * @Date 2019/11/6 21:04
 */
@Data
public class XxlSsoUser implements Serializable {
    private static final long serialVersionUID = 42L;

    // field
    private String userid;
    private String username;

    //当前用户登录版本
    private String version;
    //设置redis时间
    private int expireMinite;
    //刷新时间
    private long expireFreshTime;


}
