package net.lantrack.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import net.lantrack.module.sys.entity.SysRoleEntity;
import net.lantrack.module.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.utils.Query;

import net.lantrack.module.sys.dao.SysRoleTypeDao;
import net.lantrack.module.sys.entity.SysRoleTypeEntity;
import net.lantrack.module.sys.service.SysRoleTypeService;

/**
 *@Description
 *@Author ldahuzi
 *@Date 2019-11-19 20:50:07
 */
@Service("sysRoleTypeService")
public class SysRoleTypeServiceImpl extends ServiceImpl<SysRoleTypeDao, SysRoleTypeEntity> implements SysRoleTypeService {

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<SysRoleTypeEntity> getList() {
        List<SysRoleTypeEntity> typeList = this.baseMapper.selectList(new QueryWrapper<SysRoleTypeEntity>().orderByAsc("type_sort"));
        for(SysRoleTypeEntity roleType:typeList){
            List<SysRoleEntity> roleList = sysRoleService.queryListByTypeId(roleType.getId());
            for(SysRoleEntity role:roleList){
                role.setPermission("");
            }
            roleType.setRoles(roleList);
        }
        return typeList;
    }

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        IPage<SysRoleTypeEntity> page = this.page(
                new Query<SysRoleTypeEntity>().getPage(params),
                new QueryWrapper<SysRoleTypeEntity>()
        );

        return new PageEntity(page);
    }



}
