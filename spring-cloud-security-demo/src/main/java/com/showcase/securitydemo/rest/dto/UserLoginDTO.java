package com.showcase.securitydemo.rest.dto;

/**
 * Created by youpengfei on 2017/2/28.
 *  yoghurt登录对象的DTO
 */
public class UserLoginDTO {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
