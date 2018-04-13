package com.showcase.securitydemo.domain;

/**
 * Created by youpengfei on 2017/2/27.
 * 用户和角色绑定类
 */
public class UserRole {
    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
