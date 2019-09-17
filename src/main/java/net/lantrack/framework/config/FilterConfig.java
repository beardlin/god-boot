
package net.lantrack.framework.config;

import net.lantrack.framework.common.xss.XssFilter;
import net.lantrack.framework.security.conf.Conf;
import net.lantrack.framework.security.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;

/**
 *@Description 过滤器配置
 *@Author lantrack
 *@Date 2019/8/22  17:05
 */
@Configuration
public class FilterConfig {

//    @Value("${xxl.sso.server}")
//    private String xxlSsoServer;
//
//    @Value("${xxl.sso.logout.path}")
//    private String xxlSsoLogoutPath;
//
//    @Value("${xxl.sso.excluded.paths}")
//    private String xxlSsoExcludedPaths;

//    @Bean
//    public FilterRegistrationBean xxlSsoFilterRegistration() {
//        // xxl-sso, filter init
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setName("XxlSsoWebFilter");
//        registration.setOrder(Integer.MAX_VALUE - 2);
//        registration.addUrlPatterns("/*");
//        registration.setFilter(new TokenFilter());
////        registration.addInitParameter(Conf.SSO_SERVER, xxlSsoServer);
////        registration.addInitParameter(Conf.SSO_LOGOUT_PATH, xxlSsoLogoutPath);
////        registration.addInitParameter(Conf.SSO_EXCLUDED_PATHS, xxlSsoExcludedPaths);
//
//        return registration;
//    }

    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setEnabled(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }
}
