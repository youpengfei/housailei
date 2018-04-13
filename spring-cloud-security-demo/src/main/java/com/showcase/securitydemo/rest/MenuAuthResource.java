package com.showcase.securitydemo.rest;


import com.showcase.securitydemo.service.MenuService;
import com.showcase.securitydemo.service.MenuService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/24.
 * 角色相关接口
 */
@RestController
@RequestMapping("/api")
public class MenuAuthResource {


    @Autowired
    private MenuService menuService;


    @RequestMapping("/menu-id/authority/{authId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<Long>> getAllMenuIdsByAuthId(@PathVariable Long authId) {
        List<Long> menuIds = menuService.getMenuIdsByAuthId(authId);
        return ResponseEntity.ok(menuIds);
    }

}
