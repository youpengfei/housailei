package com.showcase.securitydemo.rest.dto.role;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/24.
 *  角色更新DTO
 */
public class RoleUpdateDTO implements Serializable {

    private static final long serialVersionUID = 4297307411825764005L;

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
