package com.showcase.securitydemo.rest.dto.menu;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/24.
 * 菜单更新实体
 */
public class MenuUpdateDTO implements Serializable {

    private static final long serialVersionUID = 7591282033210856810L;

    @NotNull(message = "更新id不能为空")
    private Long id;

    @NotBlank(message = "菜单名不能为空")
    private String name;

    private String path;

    @NotBlank(message = "不能为空")
    private String icon;


    private Integer menuType;

    @NotBlank(message = "权限不能为空")
    private String authority;

    private String menuImg;

    private Long parentId;

    private boolean flag;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }
}
