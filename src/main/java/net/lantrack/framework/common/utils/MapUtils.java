
package net.lantrack.framework.common.utils;

import java.util.HashMap;


/**
 *@Description Map工具类
 *@Author lantrack
 *@Date 2019/8/23  15:04
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
