
package net.lantrack.project.sys.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 *@Description token
 *@Author lantrack
 *@Date 2019/8/23  10:26
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
