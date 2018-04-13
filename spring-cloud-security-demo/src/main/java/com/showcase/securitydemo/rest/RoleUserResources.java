package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.domain.base.Role;
import com.showcase.securitydemo.service.RoleService;
import com.showcase.securitydemo.rest.dto.role.RoleDTO;
import com.showcase.securitydemo.rest.dto.role.UserRoleCreateDTO;
import com.showcase.securitydemo.rest.mapper.RoleMapper;
import com.showcase.securitydemo.rest.dto.role.RoleDTO;
import com.showcase.securitydemo.rest.dto.role.UserRoleCreateDTO;
import com.showcase.securitydemo.rest.mapper.RoleMapper;
import com.showcase.securitydemo.service.RoleService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/27.
 * 用户角色服务
 */
@RestController
@RequestMapping("/api/role")
public class RoleUserResources {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<RoleDTO>> getRolesByUserId(@PathVariable Long userId) {
        final List<Role> roles = roleService.getRoleByUserId(userId);
        return ResponseEntity.ok(roles.stream().map(roleMapper::domainToDto).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> bindRole(@RequestBody UserRoleCreateDTO userRoleCreateDTO) {
        roleService.bindRoles(userRoleCreateDTO.getUserId(), userRoleCreateDTO.getRoleIds());
        return ResponseEntity.ok().build();
    }


}
