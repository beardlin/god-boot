
package net.lantrack.module.sys.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 *@Description  token
 *@Author dahuzi
 *@Date 2019/10/29  17:49
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
