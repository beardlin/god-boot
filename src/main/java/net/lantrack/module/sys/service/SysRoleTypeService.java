package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysRoleTypeEntity;

import java.util.List;
import java.util.Map;
/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-19 20:50:07
 */
public interface SysRoleTypeService extends IService<SysRoleTypeEntity> {

    PageEntity queryPage(Map<String, Object> params);

    /**
     * 获取角色分类列表
     * @return
     */
    List<SysRoleTypeEntity> getList();
}

