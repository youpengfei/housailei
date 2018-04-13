package com.showcase.securitydemo.rest.dto;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/28.
 * 用户返回token的DTO
 */
public class AuthTokenDTO implements Serializable {
    private static final long serialVersionUID = -601362802913943569L;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
