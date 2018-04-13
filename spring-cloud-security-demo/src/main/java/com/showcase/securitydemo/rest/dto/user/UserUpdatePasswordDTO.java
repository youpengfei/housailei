package com.showcase.securitydemo.rest.dto.user;

import java.io.Serializable;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/23.
 * 用户更新DTO
 */
public class UserUpdatePasswordDTO implements Serializable {

    @Length(min = 6,max = 16)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Length(min = 6,max = 16)
    @NotBlank(message = "重复密码不能为空")
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
