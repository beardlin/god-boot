
package net.lantrack.project.sys.redis;


import net.lantrack.framework.common.utils.RedisKeys;
import net.lantrack.framework.common.utils.RedisUtils;
import net.lantrack.project.sys.entity.SysConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *@Description 系统配置Redis
 *@Author lantrack
 *@Date 2019/8/23  10:28
 */
@Component
public class SysConfigRedis {
    @Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfigEntity.class);
    }
}
