package com.showcase.securitydemo.rest.dto.menu;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by youpengfei on 2017/2/24.
 * 菜单创建实体
 */
public class MenuCreateDTO implements Serializable {

    private static final long serialVersionUID = -7950432190649710815L;
    @NotBlank(message = "菜单名不能为空")
    private String name;

    @NotBlank(message = "path不能为空")
    private String path;

    @NotBlank(message = "不能为空")
    private String icon;

    @NotNull(message = "类型不能为空")
    private Integer menuType;

    @NotBlank(message = "权限不能为空")
    private String authority;

    private Long parentId;

    private String menuImg;


    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
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
}
