
package net.lantrack.module.sys.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.MapEntity;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.framework.common.utils.UserUtils;
import net.lantrack.module.sys.dao.SysUserDao;
import net.lantrack.module.sys.entity.SysOfficeEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.model.SysUserModel;
import net.lantrack.module.sys.service.SysOfficeService;
import net.lantrack.module.sys.service.SysRoleService;
import net.lantrack.module.sys.service.SysUserRoleService;
import net.lantrack.module.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @Description 系统用户
 * @Author dahuzi
 * @Date 2019/10/29  17:55
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysOfficeService sysOfficeService;


    @Override
    public List<MapEntity> getUserMap(Map<String, String> parms) {
        String officeId = parms.get("officeId");
        List<Map<String, Object>> userSelect = this.baseMapper.getUserSelect(officeId);
        if (!userSelect.isEmpty()) {
            List<MapEntity> list = new ArrayList<>(userSelect.size());
            for (Map<String, Object> map : userSelect) {
                Object user_id = map.get("user_id");
                Object real_name = map.get("real_name");
                MapEntity me = new MapEntity(user_id.toString(), real_name.toString());
                list.add(me);
            }
            return list;
        }
        return null;
    }

    @Override
    public PageEntity queryPage(Map<String, Object> params) {
        String s = this.sqlStatement(SqlMethod.DELETE);
        Object username = params.get("username");
        Object userstatus = params.get("userstatus");
        Object officeId = params.get("officeid");

        IPage<SysUserEntity> page = this.page(
                new Query<SysUserEntity>().getPage(params),
                new QueryWrapper<SysUserEntity>()
                        .like(username!=null&&!"".equals(username), "username", username)
                        .eq(userstatus!=null&&!"".equals(userstatus), "status", userstatus)
                        .eq(officeId!=null&&!"".equals(officeId), "office_id", officeId)
        );
        List<SysUserEntity> records = page.getRecords();
        List<SysUserModel> models = new ArrayList<>(records.size());
        for (SysUserEntity user : records) {
            SysUserModel model = new SysUserModel();
            BeanUtil.copyProperties(user, model);
            models.add(model);
        }
        PageEntity result = new PageEntity(page);
        result.setList(models);
        return result;
    }

    @Override
    public List<String> queryAllPerms(Long userId) {
        return baseMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUserEntity queryByUserName(String username) {
        return baseMapper.queryByUserName(username);
    }

    @Override
    @Transactional
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setPassword(UserUtils.saltPass(user.getPassword(), salt));
        user.setSalt(salt);
        this.save(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        this.updateById(user);

        //检查角色是否越权
        checkRole(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    public void deleteBatch(Long[] userId) {
        this.removeByIds(Arrays.asList(userId));
    }

    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    public void statusChange(Long userId, String status) {
        this.baseMapper.changeStatus(userId, status);
    }

    /**
     * 检查角色是否越权
     */
    private void checkRole(SysUserEntity user) {
        if (user.getRoleIdList() == null || user.getRoleIdList().size() == 0) {
            return;
        }
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getCreateUserId() == Constant.SUPER_ADMIN) {
            return;
        }

        //查询用户创建的角色列表
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new GlobalException("新增用户所选角色，不是本人创建");
        }
    }


    @Override
    public Integer queryDutyCount(Long officeId, String dutyId) {
        QueryWrapper<SysUserEntity> queryWrapper = new QueryWrapper<SysUserEntity>()
                .eq("office_id", officeId).and(wraer ->
                        wraer.likeLeft("duty_ids", "," + dutyId)
                                .or().likeRight("duty_ids", dutyId + ",")
                                .or().like("duty_ids", "," + dutyId + ","));

        int count = this.count(queryWrapper);
        return count;
    }
    @Override
    public String queryMasterId(Long officeId) {
        SysOfficeEntity byId = sysOfficeService.getById(officeId);
        return byId==null?"":byId.getOMasterId();
    }
}
