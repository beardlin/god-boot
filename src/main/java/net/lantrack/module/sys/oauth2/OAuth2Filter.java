
package net.lantrack.module.sys.oauth2;

import net.lantrack.framework.common.utils.ServerStatus;
import com.google.gson.Gson;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.HttpContextUtils;
import net.lantrack.framework.security.conf.Conf;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *@Description  oauth2过滤器
 *@Author dahuzi
 *@Date 2019/10/29  17:49
 */
public class OAuth2Filter extends AuthenticatingFilter {

    private static final String SESSION_ID = Conf.SSO_SESSIONID;

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            return null;
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        //1查看是否是预请求OPTIONS，是则直接放过
        if(req.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
//        boolean login = UserUtils.isLogin();
//        //2每次登录成功都刷新token失效时间延长
//        if(login){
//            String token = getRequestToken((HttpServletRequest) request);
//            if(StringUtils.isBlank(token)){
//                return false;
//            }
//            UserUtils.freshToken(token);
//        }
//
//        return login;
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if(StringUtils.isBlank(token)){
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());

            String json = new Gson().toJson(new ReturnEntity().err(ServerStatus.UN_AUTH, "invalid token"));

            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();
            String json = new Gson().toJson(new ReturnEntity().err(ServerStatus.UN_AUTH, throwable.getMessage()));
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest){
        //从header中获取token
        String token = httpRequest.getHeader(SESSION_ID);

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = httpRequest.getParameter(SESSION_ID);
        }

        return token;
    }


}
