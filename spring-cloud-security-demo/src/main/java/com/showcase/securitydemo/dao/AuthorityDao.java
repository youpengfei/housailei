package com.showcase.securitydemo.dao;

import com.showcase.securitydemo.dao.base.BaseAuthorityDao;
import com.showcase.securitydemo.domain.AuthorityRole;
import com.showcase.securitydemo.domain.base.Authority;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Created by youpengfei on 2017/2/27.
 * 权限获取数据逻辑
 */
public interface AuthorityDao extends BaseAuthorityDao {
    List<Authority> getAuthByUserId(Long userId);

    List<Authority> getAuthByRoleId(Long roleId);

    void batchInsertIntoAuthRole(@Param("authRoles") List<AuthorityRole> authRoles);

    int deleteAuthRoleByRoleId(@Param("roleId") Long roleId);
    int deleteAuthRoleByAuthId(@Param("authId") Long authId);
}
