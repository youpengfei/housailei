package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.domain.base.Role;
import com.showcase.securitydemo.rest.dto.role.RoleCreateDTO;
import com.showcase.securitydemo.rest.dto.role.RoleDTO;
import com.showcase.securitydemo.rest.dto.role.RoleUpdateDTO;
import com.showcase.securitydemo.rest.mapper.RoleMapper;
import com.showcase.securitydemo.service.RoleService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/24.
 * 角色相关接口
 */
@RestController
@RequestMapping("/api/role")
public class RoleResource {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<RoleDTO> get(@PathVariable Long id) {
        final Role user = roleService.getRoleById(id);

        final RoleDTO roleDTO = roleMapper.domainToDto(user);
        if (roleDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(roleDTO);
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<RoleDTO>> get(RoleDTO roleDTO,Pageable pageable) {
        final Page<Role> roles = roleService.findRolePage(roleMapper.domainToDto(roleDTO),pageable);
        final Page<RoleDTO> roleDTOS = roles.map(roleMapper::domainToDto);
        return ResponseEntity.ok(roleDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> update(@RequestBody @Valid RoleUpdateDTO roleUpdateDTO) {
        Role role = roleMapper.updateDtoToDomain(roleUpdateDTO);
        return ResponseEntity.ok(roleService.updateRole(role));
    }


    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody @Valid RoleCreateDTO roleCreateDTO) {
        Role role = roleMapper.createDtoToDomain(roleCreateDTO);
        return ResponseEntity.ok(roleService.addRole(role));
    }
}
