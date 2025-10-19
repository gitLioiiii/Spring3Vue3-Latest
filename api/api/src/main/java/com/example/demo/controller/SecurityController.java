package com.example.demo.controller;

import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.TokenEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.LoginFailedException;
import com.example.demo.exception.ValidateFailedException;
import com.example.demo.service.UserService;
import com.example.demo.service.UserTokensService;
import com.example.demo.utils.ResultTemplate;
import com.example.demo.validator.UserValidateGroup;

@RestController
public class SecurityController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserTokensService userTokensService;

    public SecurityController(
        UserService userService, 
        PasswordEncoder passwordEncoder, 
        UserTokensService userTokensService
    ) {
        this.userService        = userService;
        this.userTokensService  = userTokensService;
        this.passwordEncoder    = passwordEncoder;
    }

    // 先学会创建在登录的时候token，再使用token设置登录限制
    @PostMapping("/login")
    public ResultTemplate login(
        @RequestBody @Validated({ UserValidateGroup.Login.class }) UserEntity fields,
        BindingResult bindingResult
    ) {
        ResultTemplate result	= new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        UserEntity user = this.userService.fetch(fields.getUsername()).orElseThrow(
            () -> new LoginFailedException()
        );
        if (!this.passwordEncoder.matches(fields.getPassword(), user.getPassword())) {
            throw new LoginFailedException();
        }

        TokenEntity token	= new TokenEntity();
        token.setToken(UUID.randomUUID().toString());
        // 设置token有效时间
        token.setExpireAt(LocalDateTime.now().plusHours(2));
        token.setCreatedAt(LocalDateTime.now());
        token.setUserId(user.getId());
        this.userTokensService.create(token);

        result.putPayload("user", user);
        result.putPayload("token", token);

        return result;
    }

}
