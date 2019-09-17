package net.lantrack.framework.security.store;


import net.lantrack.framework.common.utils.RedisUtils;
import net.lantrack.framework.common.utils.SpringContextUtils;
import net.lantrack.framework.security.conf.Conf;
import net.lantrack.framework.security.user.XxlSsoUser;
import net.lantrack.framework.security.util.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * local login store
 *
 * @author xuxueli 2018-04-02 20:03:11
 */
public class SsoLoginStore {


    private static  RedisUtils redis;
    //声明reid为静态，需从Bean容器中去获取
    static {
        if(SpringContextUtils.containsBean("redisUtils")){
            redis = SpringContextUtils.getBean("redisUtils",RedisUtils.class);
        }
    }

    private static int redisExpireMinite = 1440;    // 1440 minite, 24 hour

    public static void setRedisExpireMinite(int redisExpireMinite) {
        if (redisExpireMinite < 30) {
            redisExpireMinite = 30;
        }
        SsoLoginStore.redisExpireMinite = redisExpireMinite;
    }
    public static int getRedisExpireMinite() {
        return redisExpireMinite;
    }

    /**
     * get
     *
     * @param storeKey
     * @return
     */
    public static XxlSsoUser get(String storeKey) {
        String redisKey = redisKey(storeKey);
        XxlSsoUser xxlUser = redis.get(redisKey, XxlSsoUser.class);
        if(xxlUser != null){
            return xxlUser;
        }
//        Object objectValue = JedisUtil.getObjectValue(redisKey);
//        if (objectValue != null) {
//            XxlSsoUser xxlUser = (XxlSsoUser) objectValue;
//            return xxlUser;
//        }
        return null;
    }

    /**
     * remove
     *
     * @param storeKey
     */
    public static void remove(String storeKey) {
        String redisKey = redisKey(storeKey);
//        JedisUtil.del(redisKey);
        redis.delete(redisKey);
    }

    /**
     * put
     *
     * @param storeKey
     * @param xxlUser
     */
    public static void put(String storeKey, XxlSsoUser xxlUser) {
        String redisKey = redisKey(storeKey);
//        JedisUtil.setObjectValue(redisKey, xxlUser, redisExpireMinite * 60);  // minite to second
        redis.set(redisKey, xxlUser, redisExpireMinite * 60);// minite to second
    }

    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
