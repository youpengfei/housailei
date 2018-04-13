package com.showcase.securitydemo.rest.dto.authority;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/24.
 * 权限返回数据
 */
public class AuthorityDTO implements Serializable {
    private static final long serialVersionUID = 2041288860194670291L;
    //权限id
    private Long id;
    //权限名称
    private String name;
    //权限介绍
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
