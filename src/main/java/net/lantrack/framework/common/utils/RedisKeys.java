

package net.lantrack.framework.common.utils;

/**
 *@Description Redis所有Keys
 *@Author lantrack
 *@Date 2019/8/22  16:54
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }
}
