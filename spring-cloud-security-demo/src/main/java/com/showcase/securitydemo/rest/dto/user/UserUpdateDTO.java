package com.showcase.securitydemo.rest.dto.user;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/23.
 * 用户更新DTO
 */
public class UserUpdateDTO implements Serializable {


    private static final long serialVersionUID = -8854680612997631589L;

    @NotNull(message = "用户id不能为空")
    private Long id;

    private String showName;

    @NotBlank(message = "用户邮箱不能为空")
    @Email(message = "用户邮箱规则不对")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;

    //部门id
    private Long departmentId;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
