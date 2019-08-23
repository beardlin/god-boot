
package net.lantrack.project.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.project.sys.entity.SysCaptchaEntity;

import java.awt.image.BufferedImage;

/**
 *@Description 验证码
 *@Author lantrack
 *@Date 2019/8/23  14:24
 */
public interface SysCaptchaService extends IService<SysCaptchaEntity> {

    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}
