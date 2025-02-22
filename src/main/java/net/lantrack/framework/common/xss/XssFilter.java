
package net.lantrack.framework.common.xss;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
/**
 *@Description  XSS过滤器
 *@Author dahuzi
 *@Date 2019/10/28  21:58
 */
public class XssFilter implements Filter {

	@Override
	public void init(FilterConfig config) {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	@Override
	public void destroy() {
	}

}
