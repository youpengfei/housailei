package com.showcase.securitydemo.service;

import com.showcase.securitydemo.dao.AuthorityDao;
import com.showcase.securitydemo.dao.RoleDao;
import com.showcase.securitydemo.dao.UserDao;
import com.showcase.securitydemo.dao.base.BaseUserDao;
import com.showcase.securitydemo.domain.UserRole;
import com.showcase.securitydemo.domain.base.Role;
import com.showcase.securitydemo.domain.base.RoleExample;
import com.showcase.securitydemo.domain.base.User;
import com.showcase.securitydemo.exception.ServiceUncheckedException;
import com.showcase.securitydemo.exception.SystemUncheckedException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.showcase.securitydemo.security.SecurityUtils;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by youpengfei on 2017/2/20.
 * 角色的增删改查
 */
@Service
public class RoleService {

	private static Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private AuthorityDao authorityDao;

	@Autowired
	private BaseUserDao userDao;


	/**
	 * 获取指定id的角色
	 */
	public Role getRoleById(Long id) {
		final Role role = roleDao.selectByPrimaryKey(id);
		if (role != null && !role.getFlag()) {
			throw new ServiceUncheckedException("权限已经被移除").setProperty("authId", id);
		}
		return role;
	}

	/**
	 * 获取到角色
	 */
	public Page<Role> findRolePage(Role role, Pageable pageable) {
		Preconditions.checkNotNull(pageable, "分页信息不能为空");
		RoleExample roleExample = new RoleExample();
		final RoleExample.Criteria criteria = roleExample.createCriteria()
				.andFlagEqualTo(true);
		if (StringUtils.isNotBlank(role.getName())) {
			criteria.andNameLike("%" + role.getName() + "%");
		}

		if (StringUtils.isNotBlank(role.getIntro())) {
			criteria.andIntroLike("%" + role.getIntro() + "%");
		}


		final long count = roleDao.countByExample(roleExample);
		if (count > 0) {
			roleExample.setOffset(pageable.getOffset());
			roleExample.setRows(pageable.getPageSize());
			final List<Role> roles = roleDao.selectByExample(roleExample);

			if (CollectionUtils.isNotEmpty(roles)) {
				return new PageImpl<>(roles, pageable, count);
			}
		}

		return new PageImpl<>(Lists.newArrayList(), pageable, 0);
	}

	/**
	 * 删除用户角色
	 */
	@Transactional
	public void deleteRole(Long id) {
		final Role role = roleDao.selectByPrimaryKey(id);
		if (role == null || !role.getFlag()) {
			throw new SystemUncheckedException("角色不存在").setProperty("roleId", id);
		}

		//删除角色对应的权限
		authorityDao.deleteAuthRoleByRoleId(id);
		//删除和用户的关联关系
		roleDao.deleteUserRoleByRoleId(id);
		//删除角色
		roleDao.deleteByPrimaryKey(id);


	}

	/**
	 * 更新角色
	 */
	public Integer updateRole(Role role) {

		validateSameRoleName(role);

		final Role roleFromDb = roleDao.selectByPrimaryKey(role.getId());
		if (roleFromDb == null || !roleFromDb.getFlag()) {
			throw new SystemUncheckedException("角色不存在").setProperty("roleId", role.getId());
		}

		roleFromDb.setName(role.getName());
		roleFromDb.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElse(null));
		roleFromDb.setLastModifiedDate(new Date());
		roleFromDb.setIntro(role.getIntro());
		return roleDao.updateByPrimaryKey(roleFromDb);
	}

	/**
	 * 新增角色
	 */
	public Long addRole(Role role) {
		validateSameRoleName(role);

		roleDao.insertSelective(role);
		return role.getId();
	}

	private void validateSameRoleName(Role role) {
		RoleExample roleExample = new RoleExample();
		roleExample.createCriteria().andNameEqualTo(role.getName());
		final long totalCount = roleDao.countByExample(roleExample);
		if (totalCount > 0) {
			throw new ServiceUncheckedException("改角色用户名重复").setProperty("roleName", role.getName());
		}
	}

	public List<Role> getRoleByUserId(Long userId) {
		Preconditions.checkNotNull(userId, "用户id不能为空");

		return roleDao.findRoleByUserId(userId);
	}


	/**
	 * 用户绑定角色
	 *
	 * @param userId  被操作用户ID
	 * @param roleIds 被绑定角色ID
	 */
	public void bindRoles(Long userId, Long[] roleIds) {
		final int deleteCount = roleDao.deleteUserRoleByUserId(userId);
		LOGGER.info("userId:{},删除的用户的数量为：{}", userId, deleteCount);

		List<UserRole> userRoles = Lists.newArrayList();
		for (Long roleId : roleIds) {
			final UserRole userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRoles.add(userRole);
		}
		roleDao.batchInsertIntoUserRole(userRoles);
		User user = userDao.selectByPrimaryKey(userId);
	}
}
