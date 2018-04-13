package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.domain.base.User;
import com.showcase.securitydemo.service.UserService;
import com.showcase.securitydemo.rest.dto.user.UserCreateDTO;
import com.showcase.securitydemo.rest.dto.user.UserDTO;
import com.showcase.securitydemo.rest.dto.user.UserQueryDTO;
import com.showcase.securitydemo.rest.dto.user.UserUpdateDTO;
import com.showcase.securitydemo.rest.dto.user.UserUpdatePasswordDTO;
import com.showcase.securitydemo.rest.mapper.UserMapper;
import com.showcase.securitydemo.rest.dto.user.UserCreateDTO;
import com.showcase.securitydemo.rest.dto.user.UserDTO;
import com.showcase.securitydemo.rest.dto.user.UserQueryDTO;
import com.showcase.securitydemo.rest.dto.user.UserUpdateDTO;
import com.showcase.securitydemo.rest.dto.user.UserUpdatePasswordDTO;
import com.showcase.securitydemo.rest.mapper.UserMapper;
import com.showcase.securitydemo.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/22.
 * 用户相关接口
 */
@RestController
@RequestMapping("/api/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;


    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET)
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        final User user = userService.getUserById(id);

        final UserDTO userDTO = userMapper.domainToDto(user);
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(userDTO);
        }
    }


    @RequestMapping(method = RequestMethod.GET)
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Page<UserDTO>> get(UserQueryDTO userQueryDTO, Pageable pageable) {
        final User user = userMapper.queryDtoToDomain(userQueryDTO);
        final Page<UserDTO> users = userService.findUserPage(user, pageable);
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> update(@RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        User user = userMapper.updateDtoToDomain(userUpdateDTO);
        return ResponseEntity.ok(userService.updateUser(user));
    }


    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        User user = userMapper.createDtoToDomain(userCreateDTO);
        return ResponseEntity.ok(userService.addUser(user));
    }


    @GetMapping(value = "/single", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> get() {
        User user = userService.findUser();
        return ResponseEntity.ok(userMapper.domainToDto(user));
    }

    @PutMapping(value = "/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> updatePassword(@RequestBody @Valid UserUpdatePasswordDTO userUpdatePasswordDTO) {
        return ResponseEntity.ok(userService.updatePassword(userUpdatePasswordDTO));
    }
}
