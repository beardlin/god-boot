
package net.lantrack.module.sys.service.impl;


import net.lantrack.framework.common.utils.RedisUtils;
import com.google.code.kaptcha.Producer;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.module.sys.service.SysCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 *@Description  验证码管理
 *@Author dahuzi
 *@Date 2019/10/29  17:55
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl  implements SysCaptchaService {
    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            throw new GlobalException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        //5分钟后过期,将生成验证码放入Redis
        redisUtils.set(uuid,code,5*60);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        //从redis中取出，如果不存在则超时
        String cacheCode = redisUtils.get(uuid);
        if(cacheCode == null){
            return false;
        }
        redisUtils.delete(uuid);
        if(cacheCode.equalsIgnoreCase(code)){
            return true;
        }
        return false;
    }
}
