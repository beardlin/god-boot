package net.lantrack.module.sys.model;

import lombok.Data;

import java.util.List;

/**
 * 树型结构model
 */
@Data
public class TreeModel {
    /**
     *节点id
     */
    private String id;
    /**
     *节点名称
     */
    private String title;
    /**
     *节点类型
     */
    private String type;
    /**
     *字节点
     */
    private List<TreeModel> children;
}
