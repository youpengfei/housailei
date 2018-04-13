package com.showcase.securitydemo.domain;

/**
 * Created by youpengfei on 2017/2/27.
 * 角色和权限绑定对象
 */
public class AuthorityRole {
    private Long authorityId;
    private Long roleId;

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
