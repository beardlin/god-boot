
package net.lantrack.module.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.lantrack.framework.common.entity.PageEntity;
import net.lantrack.framework.common.exception.GlobalException;
import net.lantrack.framework.common.utils.Constant;
import net.lantrack.framework.common.utils.Query;
import net.lantrack.module.sys.dao.SysRoleDao;
import net.lantrack.module.sys.entity.SysRoleEntity;
import net.lantrack.module.sys.entity.SysUserEntity;
import net.lantrack.module.sys.entity.SysUserRoleEntity;
import net.lantrack.module.sys.model.UserRoleDetailModel;
import net.lantrack.module.sys.model.UserRoleModel;
import net.lantrack.module.sys.service.SysRoleMenuService;
import net.lantrack.module.sys.service.SysRoleService;
import net.lantrack.module.sys.service.SysUserRoleService;
import net.lantrack.module.sys.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 *@Description  角色管理
 *@Author dahuzi
 *@Date 2019/10/29  17:56
 */
@Service("sysRoleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleEntity> implements SysRoleService {

	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


	@Override
	public UserRoleDetailModel getUserRoleDetail(Long userId) {
		SysUserEntity user = sysUserService.getById(userId);
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		UserRoleDetailModel model = new UserRoleDetailModel();
		model.setOfficeId(user.getOfficeId());
		model.setUserId(user.getUserId());
		StringBuilder builder = new StringBuilder();
		for(Long rId:roleIdList){
			builder.append(rId).append(",");
		}
		if(builder.length()>0){
			String rids = builder.substring(0, builder.length() - 1);
			model.setRolesId(rids);
		}
		return model;
	}

	@Override
	public void roleAuth(Map<String, String> parms) {
		String roleId = parms.get("roleId");
		String permission = parms.get("permission");
		if(StringUtils.isNotBlank(roleId)){
			SysRoleEntity roleEntity = this.baseMapper.selectById(roleId);
			roleEntity.setPermission(permission);
			this.baseMapper.updateById(roleEntity);
		}
	}

	@Override
	public void deleteUserByRoleId(Map<String, String> parms) {
		String roleId = parms.get("roleId");
		String userIds = parms.get("userIds");
		String[] split = userIds.split(",");
		for(String uid:split){
			sysUserRoleService.deleteUserRole(roleId,uid);
		}
	}

	@Override
	public void configUserRole(Map<String, String> parms) {
		String roleIds = parms.get("roleIds");
		String userIds = parms.get("userIds");
		String[] roleArray = roleIds.split(",");
		List<Long> roleIdList = new ArrayList<>(roleArray.length);
		for(String rid:roleArray){
			roleIdList.add(Long.valueOf(rid));
		}
		String[] userArray = userIds.split(",");
		//配置用户角色
		for(String userId:userArray){
			sysUserRoleService.saveOrUpdate(Long.valueOf(userId),roleIdList);
		}
	}

	@Override
	public List<UserRoleModel> getUserListByRoleId(Map<String, Object> parms) {
		String roleId = parms.get("roleId")==null?"":parms.get("roleId").toString();
		String user = parms.get("userName")==null?"":parms.get("userName").toString();
		List<UserRoleModel> userList = this.baseMapper.getUserListByRoleId(user, roleId);
		for(UserRoleModel u:userList){
			u.setRoleId(roleId==null?null:Long.valueOf(roleId));
			List<String> roleNames = this.baseMapper.getRoleNameByUserId(u.getUserId() + "");
			if(!roleNames.isEmpty()){
				StringBuilder sbuilder = new StringBuilder();
				for(String roleName:roleNames){
					sbuilder.append(roleName).append(",");
				}
				if(sbuilder.length()>0){
					String rName = sbuilder.substring(0, sbuilder.length() - 1);
					u.setRoleNames(rName);
				}
			}
		}
		return userList;
	}

	@Override
	public PageEntity queryPage(Map<String, Object> params) {
		String roleName = (String)params.get("roleName");
		Long createUserId = (Long)params.get("createUserId");
		IPage<SysRoleEntity> page = this.page(
			new Query<SysRoleEntity>().getPage(params),
			new QueryWrapper<SysRoleEntity>()
				.like(StringUtils.isNotBlank(roleName),"role_name", roleName)
				.eq(createUserId != null,"create_user_id", createUserId)
		);

		return new PageEntity(page);
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(SysRoleEntity role) {
        role.setCreateTime(new Date());
        this.save(role);

        //检查权限是否越权
//        checkPrems(role);

        //保存角色与菜单关系
//        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleEntity role) {
        this.updateById(role);

        //检查权限是否越权
//        checkPrems(role);

        //更新角色与菜单关系
//        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
        sysUserRoleService.deleteBatch(roleIds);
    }


    @Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return baseMapper.queryRoleIdList(createUserId);
	}

	@Override
	public List<SysRoleEntity> queryListByTypeId(Integer typeId) {
		return this.baseMapper.selectList(
				new QueryWrapper<SysRoleEntity>().eq("role_type_id",typeId)
		);
	}


	/**
	 * 检查权限是否越权
	 */
	private void checkPrems(SysRoleEntity role){
		//如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
		if(role.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户所拥有的菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

		//判断是否越权
		if(!menuIdList.containsAll(role.getMenuIdList())){
			throw new GlobalException("新增角色的权限，已超出你的权限范围");
		}
	}
}
