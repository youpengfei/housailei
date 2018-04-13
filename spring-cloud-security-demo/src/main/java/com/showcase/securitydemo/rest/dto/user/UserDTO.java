package com.showcase.securitydemo.rest.dto.user;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/2/22.
 * 用户展示DTO
 */
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -5328614445565813807L;
    private Long id;

    private String showName;

    private String email;

    private Long departmentId;
    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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


}
