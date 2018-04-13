package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.rest.dto.authority.AuthorityCreateDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityUpdateDTO;
import com.showcase.securitydemo.rest.mapper.AuthorityMapper;
import com.showcase.securitydemo.rest.dto.authority.AuthorityCreateDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityUpdateDTO;
import com.showcase.securitydemo.rest.mapper.AuthorityMapper;
import com.showcase.securitydemo.service.AuthorityService;
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
 * 权限相关接口
 */
@RestController
@RequestMapping("/api/authority")
public class AuthorityResource {

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private AuthorityMapper authorityMapper;

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<AuthorityDTO> get(@PathVariable Long id) {
        final Authority user = authorityService.getAuthorityById(id);

        final AuthorityDTO authorityDTO = authorityMapper.domainToDto(user);
        if (authorityDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(authorityDTO);
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<AuthorityDTO>> get( AuthorityDTO authorityDTO,Pageable pageable) {

        final Page<Authority> authoritys = authorityService.findAuthorityPage(authorityMapper.dtoToDomain(authorityDTO),pageable);
        final Page<AuthorityDTO> authorityDTOS = authoritys.map(authorityMapper::domainToDto);
        return ResponseEntity.ok(authorityDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorityService.deleteAuthority(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping( method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> update(@RequestBody @Valid AuthorityUpdateDTO authorityUpdateDTO) {
        Authority authority = authorityMapper.updateDtoToDomain(authorityUpdateDTO);
        return ResponseEntity.ok(authorityService.updateAuthority(authority));
    }


    @RequestMapping( method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody @Valid AuthorityCreateDTO authorityCreateDTO) {
        Authority authority = authorityMapper.createDtoToDomain(authorityCreateDTO);
        return ResponseEntity.ok(authorityService.addAuthority(authority));
    }
}
