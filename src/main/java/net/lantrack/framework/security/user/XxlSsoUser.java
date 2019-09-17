package net.lantrack.framework.security.user;

import java.io.Serializable;
import java.util.Map;

/**
 * xxl sso user
 *
 * @author xuxueli 2018-04-02 19:59:49
 */
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


    // set get
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getExpireMinite() {
        return expireMinite;
    }

    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }

}
