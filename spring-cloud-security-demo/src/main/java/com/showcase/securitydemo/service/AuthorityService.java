package com.showcase.securitydemo.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.showcase.securitydemo.dao.AuthorityDao;
import com.showcase.securitydemo.dao.UserDao;
import com.showcase.securitydemo.domain.AuthorityRole;
import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.domain.base.AuthorityExample;
import com.showcase.securitydemo.domain.base.User;
import com.showcase.securitydemo.exception.ServiceUncheckedException;
import com.showcase.securitydemo.security.SecurityUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
 * 权限service
 */
@Service
public class AuthorityService {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthorityService.class);

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private UserDao userDao;

    public Page<Authority> findAuthorityPage(Authority authority, Pageable pageable) {
        AuthorityExample authExample = new AuthorityExample();
        final AuthorityExample.Criteria criteria = authExample.createCriteria();
        criteria.andFlagEqualTo(true);

        if (StringUtils.isNotBlank(authority.getName())) {
            criteria.andNameLike("%" + authority.getName() + "%");
        }

        if (StringUtils.isNotBlank(authority.getIntro())) {
            criteria.andIntroLike("%" + authority.getIntro() + "%");
        }

        final long totalCount = authorityDao.countByExample(authExample);
        if (totalCount > 0) {
            authExample.setOffset(pageable.getOffset());
            authExample.setRows(pageable.getPageSize());
            final List<Authority> authorities = authorityDao.selectByExample(authExample);
            if (CollectionUtils.isNotEmpty(authorities)) {
                return new PageImpl<>(authorities, pageable, totalCount);
            }
        }
        return new PageImpl<>(Lists.newArrayList(), pageable, totalCount);
    }

    /**
     * 删除权限
     * @param id
     *         权限id
     */
    @Transactional
    public void deleteAuthority(Long id) {
        final Authority authority = authorityDao.selectByPrimaryKey(id);
        if (authority == null || !authority.getFlag()) {
            throw new ServiceUncheckedException("删除的权限不存在").setProperty("authId", id);
        }
        List<User> users = userDao.findUsersByAuthId(id);
        //删除关联关系
        authorityDao.deleteAuthRoleByAuthId(id);
        //删除权限信息
        authorityDao.deleteByPrimaryKey(id);
    }

    public int updateAuthority(Authority authority) {

        validateSameNameAuth(authority);

        Authority authorityFromDb = authorityDao.selectByPrimaryKey(authority.getId());
        if (authorityFromDb == null) {
            throw new ServiceUncheckedException("更新权限不存在").setProperty("authId", authority.getId());
        }
        authorityFromDb.setName(authority.getName());
        authorityFromDb.setIntro(authority.getIntro());
        authorityFromDb.setLastModifiedDate(new Date());
        authorityFromDb.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
        int result = authorityDao.updateByPrimaryKey(authorityFromDb);
        return result;
    }

    public Long addAuthority(Authority authority) {

        validateSameNameAuth(authority);

        authority.setCreatedBy("current_user");
        authority.setCreatedDate(new Date());
        authority.setFlag(true);
        authorityDao.insertSelective(authority);
        return authority.getId();
    }

    private void validateSameNameAuth(Authority authority) {
        AuthorityExample authExample = new AuthorityExample();
        authExample.createCriteria().andNameEqualTo(authority.getName());
        final long count = authorityDao.countByExample(authExample);
        if (count > 0) {
            throw new ServiceUncheckedException("已存在同名的权限").setProperty("authName", authority.getName());
        }
    }

    public Authority getAuthorityById(Long id) {
        final Authority authority = authorityDao.selectByPrimaryKey(id);
        if (authority != null && !authority.getFlag()) {
            throw new ServiceUncheckedException("权限已经被移除").setProperty("authId", id);
        }
        return authority;
    }


    public Map<String, Set<String>> getChildrenAuthorities() {
        Map<String, Set<String>> map = new HashMap<>();
        AuthorityExample queryParam = new AuthorityExample();
        queryParam.createCriteria()
                .andFlagEqualTo(true)
                .andChildAuthIsNotNull();
        List<Authority> authoritiesHaveChild = authorityDao.selectByExample(queryParam);
        for (Authority authority : authoritiesHaveChild) {
            String[] authArray = authority.getChildAuth().split(",");
            map.put(authority.getName(), Sets.newHashSet(authArray));
        }
        return map;
    }


    public List<Authority> getAuthByUserId(Long userId) {
        return authorityDao.getAuthByUserId(userId);
    }

    public List<String> getAuthCodesByUserId(Long userId) {
        final List<Authority> authByUserId = authorityDao.getAuthByUserId(userId);
        if (CollectionUtils.isNotEmpty(authByUserId)) {
            return authByUserId.stream().map(Authority::getName).collect(Collectors.toCollection(LinkedList::new));
        }
        return Lists.newArrayList();
    }

    public List<Authority> getAuthByRoleId(Long roleId) {
        return authorityDao.getAuthByRoleId(roleId);
    }


    @Transactional
    public void bindAuth(Long roleId, Long[] authorityIds) {
        final int deleteCount = authorityDao.deleteAuthRoleByRoleId(roleId);
        LOGGER.info("roleId:{},删除的权限数量为：{}", roleId, deleteCount);
        List<AuthorityRole> authRoles = Lists.newArrayList();
        for (Long authId : authorityIds) {
            final AuthorityRole authorityRole = new AuthorityRole();
            authorityRole.setRoleId(roleId);
            authorityRole.setAuthorityId(authId);
            authRoles.add(authorityRole);
        }
        authorityDao.batchInsertIntoAuthRole(authRoles);
        List<User> users = userDao.findUsersByRoleId(roleId);
    }
}
