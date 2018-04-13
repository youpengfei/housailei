package com.showcase.securitydemo.rest.dto.role;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/24.
 * 前台创建角色传入的值
 */
public class RoleCreateDTO implements Serializable {
    private static final long serialVersionUID = -7345699772442988666L;

    private String name;

    private String intro;

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
