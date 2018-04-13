package com.showcase.securitydemo.rest.dto.authority;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/24.
 * 权限创建DTO
 */
public class AuthorityCreateDTO implements Serializable {
    private static final long serialVersionUID = -303979958388050660L;
    //权限名
    @NotBlank(message = "权限名称不能为空")
    private String name;
    //权限介绍
    @NotBlank(message = "介绍不能为空")
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
