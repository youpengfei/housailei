package com.showcase.securitydemo.dao;

import com.showcase.securitydemo.dao.base.BaseUserDao;
import com.showcase.securitydemo.domain.base.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * Created by youpengfei on 2017/2/23.
 * 角色数据库相关操作
 */
public interface UserDao extends BaseUserDao {

	List<User> findUsersByRoleId(@Param("roleId") Long roleId);

	List<User> findUsersByAuthId(@Param("authId") Long authId);
}
