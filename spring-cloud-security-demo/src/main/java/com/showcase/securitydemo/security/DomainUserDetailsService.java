package com.showcase.securitydemo.security;

import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.domain.base.User;
import com.showcase.securitydemo.exception.ServiceUncheckedException;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.service.UserService;
import com.showcase.securitydemo.service.AuthorityService;
import com.showcase.securitydemo.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;


    @Override
    public UserDetails loadUserByUsername(final String username) {
        log.info("用户名:{}", username);
        User user = userService.getUserByEmail(username);

        if (user == null) {
            throw new ServiceUncheckedException("用户名或密码错误");
        } else {
            //权限列表
            final List<Authority> authorities = authorityService.getAuthByUserId(user.getId());
            return createSpringSecurityUser(user, authorities);
        }
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(User user, List<Authority> authorities) {
        if (!user.getFlag()) {
            throw new UserNotActivatedException("User " + user.getShowName() + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                grantedAuthorities);
    }
}
