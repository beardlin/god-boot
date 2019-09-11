package net.lantrack.framework.common.entity;

import java.io.Serializable;

/**
 *@Description 存放k-v结构
 *@Author lantrack
 *@Date 2019/9/11  15:07
 */
public class MapEntity implements Serializable {
    private String k;//key
    private String v;//val

    public MapEntity(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
