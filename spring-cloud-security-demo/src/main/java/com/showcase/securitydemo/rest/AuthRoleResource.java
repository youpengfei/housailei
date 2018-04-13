package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.rest.dto.authority.AuthRoleCreateDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityDTO;
import com.showcase.securitydemo.rest.mapper.AuthorityMapper;
import com.showcase.securitydemo.service.AuthorityService;
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
 *
 */
@RestController
@RequestMapping("/api/authority")
public class AuthRoleResource {
    @Autowired
    private AuthorityService authorityService;


    @Autowired
    private AuthorityMapper authorityMapper;

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public ResponseEntity<List<AuthorityDTO>> getRolesByUserId(@PathVariable Long roleId) {
        final List<Authority> roles = authorityService.getAuthByRoleId(roleId);
        return ResponseEntity.ok(roles.stream().map(authorityMapper::domainToDto).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/role", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> bindRole(@RequestBody AuthRoleCreateDTO authRoleCreateDTO) {
        authorityService.bindAuth(authRoleCreateDTO.getRoleId(), authRoleCreateDTO.getAuthorityIds());
        return ResponseEntity.ok().build();
    }
}
