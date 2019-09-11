
package net.lantrack.framework.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;


/**
 *@Description 生成验证码配置
 *@Author lantrack
 *@Date 2019/8/22  17:06
 */
@ConfigurationProperties(
        prefix = "godboot.captcha"
)
@Component
public class KaptchaConfig {

    private String length;
    private String chars;

    @Bean
    public DefaultKaptcha producer() {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "4");
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        //验证码长度
        if(length!=null && !"".equals(length)){
            properties.put("kaptcha.textproducer.char.length", length);
        }
        //验证码生成字符
        if(chars!=null && !"".equals(chars)){
            properties.put("kaptcha.textproducer.char.string", chars);
        }
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getChars() {
        return chars;
    }

    public void setChars(String chars) {
        this.chars = chars;
    }
}
