package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.rest.dto.authority.AuthorityDTO;
import com.showcase.securitydemo.rest.mapper.AuthorityMapper;
import com.showcase.securitydemo.rest.dto.authority.AuthorityDTO;
import com.showcase.securitydemo.rest.mapper.AuthorityMapper;
import com.showcase.securitydemo.service.AuthorityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/27.
 *  
 */
@RestController
@RequestMapping("/api/authority")
public class UserAuthorityResource {
    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<AuthorityDTO>> getRolesByUserId(@PathVariable Long userId) {
        final List<Authority> roles = authorityService.getAuthByUserId(userId);
        return ResponseEntity.ok(authorityMapper.domainsToDtos(roles));
    }
}
