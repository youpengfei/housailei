package com.showcase.securitydemo.rest.dto.authority;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/24.
 * 权限更新DTO对象，传入对象
 */
public class AuthorityUpdateDTO implements Serializable {


    private static final long serialVersionUID = -2708479372676611479L;
    //权限id
    @NotNull(message = "唯一标识不能为空")
    private Long id;
    //权限名称
    @NotBlank(message = "权限名称不能为空")
    private String name;
    //权限介绍
    @NotBlank(message = "介绍不能为空")
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
