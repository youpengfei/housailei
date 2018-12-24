package com.showcase.cassandrademo.controller;

import com.showcase.cassandrademo.domain.Users;
import com.showcase.cassandrademo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cassandra")
public class UsersController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Page<Users>> getAll(
            @RequestParam(value = "pageState", required = false) String pageState,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.getUserPage(pageState, pageSize));
    }

    @PostMapping(value = "/users", consumes = "application/json")
    public ResponseEntity<Void> createUser(@RequestBody Users users) {
        userService.createUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
