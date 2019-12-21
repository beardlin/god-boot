

package net.lantrack.framework.common.utils;

/**
 *@Description
 *@Author dahuzi
 *@Date 2019/10/28  21:46
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
