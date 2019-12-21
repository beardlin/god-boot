
package net.lantrack.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.ReturnEntity;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.sys.dao.SysUserTokenDao;
import net.lantrack.module.sys.entity.SysUserTokenEntity;
import net.lantrack.module.sys.oauth2.TokenGenerator;
import net.lantrack.module.sys.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *@Description  用户token
 *@Author dahuzi
 *@Date 2019/10/29  17:55
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenDao, SysUserTokenEntity> implements SysUserTokenService {


	@Override
	public void updateToken(String token) {
		//当前时间
		Date now = new Date();
		//过期时间(分钟转换为毫秒)
		Date expireTime = new Date(now.getTime() + UserUtils.tokenExpireMinite * 60 * 1000);
		baseMapper.updateToken(token,expireTime,now);
	}

	@Override
	public ReturnEntity createToken(String token,long userId) {

		//当前时间
		Date now = new Date();
		//过期时间(分钟转换为毫秒)
		Date expireTime = new Date(now.getTime() + UserUtils.tokenExpireMinite * 60 * 1000);
		//判断是否生成过token
		SysUserTokenEntity tokenEntity = this.getById(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//保存token
			this.save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//更新token
			this.updateById(tokenEntity);
		}
		ReturnEntity r = new ReturnEntity();
		r.put("token", token).put("expire", UserUtils.tokenExpireMinite);
		return r;
	}

	@Override
	public void logout(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();
		//修改成任意token，则原来的失效
		SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
		tokenEntity.setUserId(userId);
		tokenEntity.setToken(token);
		this.updateById(tokenEntity);
	}
}
