package com.example.demo.controller;


import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResultTemplate;

// @CrossOrigin
@RestController
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResultTemplate login(@RequestBody UserEntity login_request, BindingResult bindingResult) {
        ResultTemplate result = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            result.setStatus(false);
            result.setMessage("请求参数错误");
            return result;
        }

        String username = login_request.getUsername();
        String password = login_request.getPassword();

        if (username == null || username.trim().isEmpty()) {
            result.setStatus(false);
            result.setMessage("用户名不能为空");
            return result;
        }

        if (password == null || password.trim().isEmpty()) {
            result.setStatus(false);
            result.setMessage("密码不能为空");
            return result;
        }

        try {
            UserEntity user = userService.login(username, password);
            if (user != null) {
                result.setStatus(true);
                result.setMessage("登录成功");
                result.putPayload("user", user);
            } else {
                result.setStatus(false);
                result.setMessage("用户名或密码错误");
            }
        } catch (Exception e) {
            result.setStatus(false);
            result.setMessage("登录失败：" + e.getMessage());
        }

        return result;
    }
}
