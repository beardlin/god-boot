
package net.lantrack.module.sys.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.framework.security.store.SsoLoginStore;
import net.lantrack.framework.security.store.SsoSessionIdHelper;
import net.lantrack.framework.security.user.XxlSsoUser;
import net.lantrack.framework.security.util.SsoTokenLoginHelper;
import net.lantrack.module.sys.entity.SysMenuEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.form.SysLoginForm;
import net.lantrack.module.sys.service.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStreamImpl;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 *@Description  登录
 *@Author dahuzi
 *@Date 2019/10/29  17:40
 */
@RestController
public class SysLoginController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private SysMenuService sysMenuService;
	@Autowired
	private ShiroService shiroService;

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");
		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 验证码
	 */
//	@GetMapping("captcha.jpg")
//	public String captcha(String uuid)throws IOException {
//		//获取图片验证码
//		BufferedImage image = sysCaptchaService.getCaptcha(uuid);
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		ImageIO.write(image, "jpg", out);
//		byte[] bytes = out.toByteArray();
//		return  new String(Base64.encodeBase64(bytes));
//	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public ReturnEntity login(@RequestBody SysLoginForm form)throws IOException {
		//校验验证码
//		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
//		if(!captcha){
//			return getR().err("验证码不正确");
//		}
		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());
		//账号不存在、密码错误
		if(user == null || !UserUtils.comparePass(form.getPassword(),user.getSalt(),user.getPassword())) {
			return getR().err("账号或密码不正确");
		}
		//账号锁定
		if(user.getStatus() == 0){
			return getR().err("账号已被锁定,请联系管理员");
		}
		//1 全局sso user 登录，放入redis，供服务之间认证使用
		String token = ssoLogin(user);
		//2 将token保存到数据库，供系统认证使用
		ReturnEntity r = sysUserTokenService.createToken(token,user.getUserId());
		List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(user.getUserId());
		Set<String> permissions = shiroService.getUserPermissions(user.getUserId());
		return r.put("menuList", menuList).put("permissions", permissions);
	}


	//将用户信息保存到XxlSso，供各服务模块调用
	private String ssoLogin(SysUserEntity user ){
		XxlSsoUser xxlUser = new XxlSsoUser();
		xxlUser.setUserid(String.valueOf(user.getUserId()));
		xxlUser.setUsername(user.getUsername());
		xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
		//全局失效时间和系统失效时间要一致
		xxlUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
		xxlUser.setExpireFreshTime(System.currentTimeMillis());
		// 生成全局token sessionId + storeKey
		String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);
		// 全局登录，持久化全局token
		SsoTokenLoginHelper.login(sessionId, xxlUser);
		return sessionId;
	}
	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public ReturnEntity logout(HttpServletRequest request) {
		SysUserEntity userEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		//在当前系统失效
		sysUserTokenService.logout(userEntity.getUserId());
		//在全局模块化服务之间失效
		SsoTokenLoginHelper.logout(request);
		return getR();
	}

}
