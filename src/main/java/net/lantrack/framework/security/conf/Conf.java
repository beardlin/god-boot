package net.lantrack.framework.security.conf;


import net.lantrack.framework.common.entity.ReturnEntity;
import org.apache.http.HttpStatus;

/**
 * conf
 *
 * @author xuxueli 2018-04-02 19:18:04
 */
public class Conf {

    /**
     * sso sessionid, between browser and sso-server (web + token client)
     */
    public static final String SSO_SESSIONID = "token";


    /**
     * redirect url (web client)
     */
    public static final String REDIRECT_URL = "redirect_url";

    /**
     * sso user, request attribute (web client)
     */
    public static final String SSO_USER = "xxl_sso_user";


    /**
     * sso server address (web + token client)
     */
    public static final String SSO_SERVER = "sso_server";

    /**
     * login url, server relative path (web client)
     */
    public static final String SSO_LOGIN = "/login";
    /**
     * logout url, server relative path (web client)
     */
    public static final String SSO_LOGOUT = "/logout";


    /**
     * logout path, client relatice path
     */
    public static final String SSO_LOGOUT_PATH = "SSO_LOGOUT_PATH";

    /**
     * excluded paths, client relatice path, include path can be set by "filter-mapping"
     */
    public static final String SSO_EXCLUDED_PATHS = "SSO_EXCLUDED_PATHS";


    /**
     * login fail result
     */
    public static final ReturnEntity SSO_LOGIN_FAIL_RESULT = new ReturnEntity();

    static {
        SSO_LOGIN_FAIL_RESULT.err(HttpStatus.SC_UNAUTHORIZED,"server not authorized");
    }

}
