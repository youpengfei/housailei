package com.showcase.securitydemo.rest.dto.user;

import java.io.Serializable;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/24.
 */
public class UserCreateDTO implements Serializable {
    private static final long serialVersionUID = -6815948335092263533L;

    @NotBlank(message = "不能为空")
    private String showName;

    @Email(message = "邮箱格式不对")
    @NotBlank(message = "不能为空")
    private String email;

    @NotBlank
    @Length(min = 8, message = "密码长度最小八位")
    private String password;

    //部门id
    private Long departmentId;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
