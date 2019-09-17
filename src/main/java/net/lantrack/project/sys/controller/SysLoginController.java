
package net.lantrack.project.sys.controller;

import net.lantrack.framework.common.component.BaseController;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.security.store.SsoLoginStore;
import net.lantrack.framework.security.store.SsoSessionIdHelper;
import net.lantrack.framework.security.user.XxlSsoUser;
import net.lantrack.framework.security.util.SsoTokenLoginHelper;
import net.lantrack.project.sys.entity.SysUserEntity;
import net.lantrack.project.sys.form.SysLoginForm;
import net.lantrack.project.sys.service.SysCaptchaService;
import net.lantrack.project.sys.service.SysUserService;
import net.lantrack.project.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 *@Description 登录
 *@Author lantrack
 *@Date 2019/8/23  10:14
 */
@RestController
public class SysLoginController extends BaseController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;

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
	 * 登录
	 */
	@PostMapping("/sys/login")
	public ReturnEntity login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return getR().err("验证码不正确");
		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return getR().err("账号或密码不正确");
		}
		//账号锁定
		if(user.getStatus() == 0){
			return getR().err("账号已被锁定,请联系管理员");
		}
        //方案一 放入redis
		// 1、make xxl-sso user
		XxlSsoUser xxlUser = new XxlSsoUser();
		xxlUser.setUserid(String.valueOf(user.getUserId()));
		xxlUser.setUsername(user.getUsername());
		xxlUser.setVersion(UUID.randomUUID().toString().replaceAll("-", ""));
		xxlUser.setExpireMinite(SsoLoginStore.getRedisExpireMinite());
		xxlUser.setExpireFreshTime(System.currentTimeMillis());
		// 2、generate sessionId + storeKey
		String sessionId = SsoSessionIdHelper.makeSessionId(xxlUser);
		// 3、login, store storeKey
		SsoTokenLoginHelper.login(sessionId, xxlUser);

		//方案二 生成token，并保存到数据库
		ReturnEntity r = sysUserTokenService.createToken(sessionId,user.getUserId());
		return r;
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public ReturnEntity logout() {
		SysUserEntity userEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		sysUserTokenService.logout(userEntity.getUserId());
		return getR();
	}
	
}
