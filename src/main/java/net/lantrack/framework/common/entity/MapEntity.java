package net.lantrack.framework.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *@Description Key - Value存储
 *@Author dahuzi
 *@Date 2019/10/28  21:42
 */
@Data
public class MapEntity implements Serializable {
    private String k;//key
    private String v;//val

    public MapEntity(String k, String v) {
        this.k = k;
        this.v = v;
    }

}
