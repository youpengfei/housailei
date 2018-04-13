package com.showcase.securitydemo.rest.dto.role;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/24.
 * 角色查询和返回dto对象
 */
public class RoleDTO implements Serializable {
    private static final long serialVersionUID = -6842242286207868511L;
    private Long id;

    private String name;

    private String intro;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
