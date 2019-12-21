package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.MapEntity;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysDictEntity;

import java.util.List;
import java.util.Map;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
public interface SysDictService extends IService<SysDictEntity> {

    /**
     * 获取字典值
     * @param type
     * @param key
     * @return
     */
    String getDictValue(String type,String key);

    /**
     * 字典分类下拉
     * @return
     */
    List<MapEntity> getDictKind();

    /**
     * 获取指定类型的下拉字典
     * @param type
     * @return
     */
    List<MapEntity> getDictSelect(String type);

    PageEntity queryPage(Map<String, Object> params);
}

