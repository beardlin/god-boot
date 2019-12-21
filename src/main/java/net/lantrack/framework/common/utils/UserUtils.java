
package net.lantrack.framework.common.utils;

import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.service.SysUserTokenService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *@Description Shiro获取系统用户工具类
 *@Author dahuzi
 *@Date 2019/10/28  21:45
 */
public class UserUtils {

	public static SysUserTokenService tokenService = null;
	static {
		if(SpringContextUtils.containsBean("sysUserTokenService")){
			tokenService = SpringContextUtils.getBean("sysUserTokenService", SysUserTokenService.class);
		}
	}

	//Token过期时间（全局和系统统一）24*60分钟
	public static int tokenExpireMinite = 1440;

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUserEntity getUserEntity() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	public static String getUserName() {
		return getUserEntity().getUsername();
	}

	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	//从Session中获取验证码
	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new GlobalException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}
	//sha256加密  密码
	public static String saltPass(String password,String salt){
		return new Sha256Hash(password, salt).toHex();
	}

	//验证密码
	public static boolean comparePass(String password,String salt,String targetPass){
		return new Sha256Hash(password, salt).toHex().equals(targetPass);
	}
	//访问成功刷新Token过期时间
	public static void freshToken(String token){
		//开启线程进行后台更新
		ExecutorService threadPool = Executors.newCachedThreadPool();
		threadPool.execute(()->{
			tokenService.updateToken(token);
		});
	}




}
