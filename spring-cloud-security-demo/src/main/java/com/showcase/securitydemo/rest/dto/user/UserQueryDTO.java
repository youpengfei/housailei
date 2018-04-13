package com.showcase.securitydemo.rest.dto.user;

import java.io.Serializable;

/**
 * Created by youpengfei on 2017/5/4.
 *
 */
public class UserQueryDTO extends UserDTO implements Serializable {

    private static final long serialVersionUID = 3338384728382537762L;

    private Long departmentId;

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
