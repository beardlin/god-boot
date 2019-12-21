package net.lantrack.module.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.module.sys.entity.SysOfficeEntity;
import net.lantrack.module.sys.model.TreeModel;
import java.util.List;
import java.util.Map;
/**
 *@Description 组织机构表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
public interface SysOfficeService extends IService<SysOfficeEntity> {


    /**
     * 停用部门
     * @param parms
     */
    void stop(Map<String,Object> parms);

    PageEntity queryPage(Map<String, Object> params);

    /**
     * 查询子部门
     * @param params
     * @return
     */
    List<SysOfficeEntity> queryByPid(Map<String, Object> params);

    TreeModel getTree();
}

