package net.lantrack.module.sys.service.impl;

import net.lantrack.framework.common.component.service.SqlService;
import net.lantrack.module.sys.model.TreeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.sys.dao.SysOfficeDao;
import net.lantrack.module.sys.entity.SysOfficeEntity;
import net.lantrack.module.sys.service.SysOfficeService;

/**
 *@Description 组织机构表
 *@Author ldahuzi
 *@Date 2019-11-17 19:30:34
 */
@Service("sysOfficeService")
public class SysOfficeServiceImpl extends ServiceImpl<SysOfficeDao, SysOfficeEntity> implements SysOfficeService {


    @Override
    public TreeModel getTree() {
        return this.baseMapper.getTree("1");
    }

    @Override
    public void stop(Map<String, Object> parms) {
        this.baseMapper.stop(parms.get("id"),parms.get("status"));
    }

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<SysOfficeEntity> page = this.page(
                new Query<SysOfficeEntity>().getPage(params),
                new QueryWrapper<SysOfficeEntity>()
        );

        return new PageEntity(page);
    }

    @Override
    public List<SysOfficeEntity> queryByPid(Map<String, Object> params) {
        Object pid = params.get("pid");
        Object oName = params.get("oName");
        Object oStatus = params.get("oStatus");
        if(pid==null || "".equals(pid)){
            pid = "1";
        }
        List<SysOfficeEntity> list = this.baseMapper.selectList(
                new QueryWrapper<SysOfficeEntity>()
                        .like(oName != null && !"".equals(oName), "o_name", oName)
                        .eq(oStatus != null && !"".equals(oStatus), "o_status", oStatus)
                        .eq("p_id", pid)
        );
        return list;
//        return this.baseMapper.queryByPid((Long) pid);
    }



}
