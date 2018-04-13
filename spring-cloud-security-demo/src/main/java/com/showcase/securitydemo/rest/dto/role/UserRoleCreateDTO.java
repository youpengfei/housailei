package com.showcase.securitydemo.rest.dto.role;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/27.
 * 用户绑定角色DTO
 */
public class UserRoleCreateDTO implements Serializable {
    private static final long serialVersionUID = -2773312085055262592L;
    private Long[] roleIds;
    private Long userId;

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
