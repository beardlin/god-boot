
package net.lantrack.framework.common.utils;

import java.util.HashMap;


/**
 *@Description
 *@Author dahuzi
 *@Date 2019/10/28  21:47
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
