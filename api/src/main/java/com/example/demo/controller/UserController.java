package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.ValidateFailedException;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResultTemplate;
import com.example.demo.validator.UserValidateGroup;

// import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private Log log = LogFactory.getLog(getClass());

    private final UserService userService;

    public UserController(
        UserService userService
    ) {
        this.userService    = userService;
    }

    @GetMapping("/")
    public ResultTemplate index(
        @RequestParam(name = "keywords", required = false) String keywords
    ) {
        Map<String, Object> filter  = new HashMap<>();
        filter.put("keywords", keywords);

        List<UserEntity> users = this.userService.fetch(filter);
        
        ResultTemplate result = new ResultTemplate();
        Map<String, Object> payload = new HashMap<>();
        payload.put("user", users);
        result.setPayload(payload);
        
        return result;
    }

    @PostMapping("/create")
    public ResultTemplate create(
        @RequestBody @Validated({UserValidateGroup.Create.class}) UserEntity user, 
        BindingResult bindingResult
    ) throws ValidateFailedException {
        ResultTemplate result = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }
        
        // 设置注册时间
        user.setRegisteredAt(java.time.LocalDateTime.now());
        
        this.userService.create(user);

        return result;
    }

    @PostMapping("/update/{id}")
    public UserEntity update(
        @PathVariable("id") Integer id,
        @RequestBody UserEntity fields
    ) {
        UserEntity user = this.userService.fetch(id);

        if (fields.getName() != null) {
            user.setName(fields.getName());
        }
        if (fields.getPassword() != null && !fields.getPassword().isBlank()) {
            user.setPassword(fields.getPassword());
        }
        if (fields.getAvatar() != null) {
            user.setAvatar(fields.getAvatar());
        }
        
        Integer rows    = this.userService.update(user);

        this.log.info(rows);

        return user;
    }

    @PostMapping("/remove/{id}")
    public Boolean remove(
        @PathVariable("id") Integer id
    ) {
        UserEntity user = this.userService.fetch(id);

        Boolean result  = this.userService.remove(user) == 1;

        return result;
    }

    @GetMapping("/{id}")
    public UserEntity fetch(
        @PathVariable("id") Integer id
    ) {
        return this.userService.fetch(id);
    }

}
