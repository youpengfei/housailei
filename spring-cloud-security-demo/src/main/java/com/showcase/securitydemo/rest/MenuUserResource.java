package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.rest.dto.menu.MenuDTO;
import com.showcase.securitydemo.security.SecurityUtils;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.service.MenuService;
import com.showcase.securitydemo.rest.dto.menu.MenuDTO;
import com.showcase.securitydemo.security.SecurityUtils;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.service.MenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/3/13.
 * 用户菜单接口
 */
@RestController
@RequestMapping("/api/menu/user")
public class MenuUserResource {


    @Autowired
    private MenuService menuService;

    @Autowired
    private AuthorityService authorityService;


    /**
     * 获取当前登录用户的菜单，树状结构
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<MenuDTO>> getCurrentUser() {
        final List<String> currentUserRoles = SecurityUtils.getCurrentUserRoles();
        final List<MenuDTO> allMenuByAuths = menuService.getAllMenuByAuths(currentUserRoles);
        return ResponseEntity.ok(allMenuByAuths);
    }


    /**
     * 获取指定用户的权限树状结构
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @Secured("ADMIN")
    public ResponseEntity<List<MenuDTO>> getMenusByUserId(@PathVariable Long userId) {
        final List<String> currentUserRoles = authorityService.getAuthCodesByUserId(userId);
        final List<MenuDTO> allMenuByAuths = menuService.getAllMenuByAuths(currentUserRoles);
        return ResponseEntity.ok(allMenuByAuths);
    }

}
