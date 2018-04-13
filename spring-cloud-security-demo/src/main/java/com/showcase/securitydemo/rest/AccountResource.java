package com.showcase.securitydemo.rest;

import com.showcase.securitydemo.rest.dto.AuthTokenDTO;
import com.showcase.securitydemo.rest.dto.UserLoginDTO;
import com.showcase.securitydemo.security.jwt.TokenProvider;
import com.showcase.securitydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/24.
 * 账号相关接口
 */
@RestController
@RequestMapping("/api")
public class AccountResource {


    @Autowired
    private UserService userService;

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;


    @PostMapping(value = "authenticate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthTokenDTO> login(UserLoginDTO userLoginDTO) {
        AuthTokenDTO authToken = new AuthTokenDTO();
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDTO.getEmail(),
                        userLoginDTO.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final String token = tokenProvider.createToken(authentication, true);
        authToken.setToken(token);
        // Return the token
        return ResponseEntity.ok(authToken);

    }

}
