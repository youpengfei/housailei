package com.showcase.securitydemo.rest.dto.menu;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youpengfei on 2017/2/24.
 * 菜单DTO
 */
public class MenuDTO implements Serializable {
    private static final long serialVersionUID = -3531871144497852536L;
    private Long id;
    private String name;

    private String path;

    private String icon;

    private Integer menuType;

    private String authority;

    private Long parentId;

    private boolean hidden;

    private String menuImg;

    private Boolean isModule;

    private List<MenuDTO> children;

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



    public List<MenuDTO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDTO> children) {
        this.children = children;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public String getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(String menuImg) {
        this.menuImg = menuImg;
    }

    public Boolean getIsModule() {
        return isModule;
    }

    public void setIsModule(Boolean isModule) {
        this.isModule = isModule;
    }
}
