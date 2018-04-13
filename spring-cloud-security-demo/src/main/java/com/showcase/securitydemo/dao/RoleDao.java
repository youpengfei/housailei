package com.showcase.securitydemo.dao;

import com.showcase.securitydemo.dao.base.BaseRoleDao;
import com.showcase.securitydemo.domain.UserRole;
import com.showcase.securitydemo.domain.base.Role;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Created by youpengfei on 2017/2/23.
 * 角色数据库相关操作
 */
public interface RoleDao extends BaseRoleDao {

    List<Role> findRoleByUserId(Long userId);

    void batchInsertIntoUserRole(@Param("userRoles") List<UserRole> userRoles);

    int deleteUserRoleByUserId(@Param("userId") Long userId);

    int deleteUserRoleByRoleId(@Param("roleId") Long roleId);

}
