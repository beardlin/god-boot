package net.lantrack.framework.security.store;


import net.lantrack.framework.common.utils.RedisUtils;
import net.lantrack.framework.common.utils.SpringContextUtils;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.framework.security.conf.Conf;
import net.lantrack.framework.security.user.XxlSsoUser;

/**
 * @Description
 * @Author dahuzi
 * @Date 2019/11/6 21:04
 */
public class SsoLoginStore {


    private static  RedisUtils redis;
    //声明redis为静态，需从Bean容器中去获取
    static {
        if(SpringContextUtils.containsBean("redisUtils")){
            redis = SpringContextUtils.getBean("redisUtils",RedisUtils.class);
        }
    }
    //单点登录全局失效时间定义,与系统token失效时间统一
    private static int redisExpireMinite = UserUtils.tokenExpireMinite;

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
        redis.set(redisKey, xxlUser, xxlUser.getExpireMinite() * 60);// minite to second
    }

    private static String redisKey(String sessionId){
        return Conf.SSO_SESSIONID.concat("#").concat(sessionId);
    }

}
