package com.showcase.securitydemo.rest.dto.authority;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/27.
 * 绑定权限DTO类
 */
public class AuthRoleCreateDTO implements Serializable {
    private static final long serialVersionUID = 7249481683051801579L;
    private Long roleId;
    private Long[] authorityIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long[] getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(Long[] authorityIds) {
        this.authorityIds = authorityIds;
    }
}
