package com.example.demo.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ValidateFailedException;
import com.example.demo.service.UserService;
import com.example.demo.utils.Pagination;
import com.example.demo.utils.ResultTemplate;
import com.example.demo.validator.UserValidateGroup;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(
        UserService userService, 
        PasswordEncoder passwordEncoder
    ) {
        this.userService        = userService;
        this.passwordEncoder    = passwordEncoder;
    }

    @GetMapping("")
    public ResultTemplate index(
        @RequestParam(name = "page", required = false) Integer page, 
        @RequestParam(name = "pageSize", required = false) Integer pageSize, 
        @RequestParam(name = "keywords", required = false) String keywords
    ) {
        ResultTemplate result   = new ResultTemplate();
        Map<String, Object> filter  = new HashMap<>();

        if (keywords != null) {
            filter.put("keywords", keywords);
        }

        Integer total       = this.userService.count(filter);

        Pagination pagination   = Pagination.paginate(total, pageSize, page);

        filter.put("offset", pagination.getOffset());
        filter.put("limit", pagination.getLimit());
        result.putPayload("users", this.userService.fetch(filter));

        result.putPayload("pagination", pagination);

        return result;
    }

    @PostMapping("/create")
    public ResultTemplate create(
        @RequestBody @Validated({UserValidateGroup.Create.class}) UserEntity user, 
        BindingResult bindingResult
    ) {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        // 检查用户名是否已存在
        if (this.userService.fetch(user.getUsername()).isPresent()) {
            result.setStatus(false);
            result.setMessage("用户名已存在，请选择其他用户名");
            return result;
        }

        user.setPassword(
            this.passwordEncoder.encode(user.getPassword())
        );
        user.setRegisteredAt(LocalDateTime.now());
        this.userService.create(user);

        return result;
    }

    @PostMapping("/update")
    public ResultTemplate update(
        @RequestBody @Validated(UserValidateGroup.Update.class) UserEntity user, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

        // 验证用户是否存在
        UserEntity existingUser = this.userService.fetch(user.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        // 更新用户信息
        existingUser.setName(user.getName());
        
        // 如果提供了新密码，则更新密码
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(this.passwordEncoder.encode(user.getPassword()));
        }
        
        // 如果提供了新头像，则更新头像
        if (user.getAvatar() != null) {
            existingUser.setAvatar(user.getAvatar());
        }

        this.userService.update(existingUser);

        return result;
    }

    @PostMapping("/remove")
    public ResultTemplate remove(
        @RequestBody @Validated(UserValidateGroup.Remove.class) UserEntity fields, 
        BindingResult bindingResult
    ) throws ValidateFailedException, NotFoundException {
        ResultTemplate result   = new ResultTemplate();

        if (bindingResult.hasErrors()) {
            throw new ValidateFailedException();
        }

       UserEntity user  = this.userService.fetch(fields.getId()).orElseThrow(
            () -> new NotFoundException()
        );

        user.setDeletedAt(LocalDateTime.now());
        this.userService.remove(user);

        return result;
    }

    @GetMapping("/{id}")
    public ResultTemplate fetch(
        @PathVariable("id") Integer id
    ) throws NotFoundException {
        ResultTemplate result   = new ResultTemplate();

       UserEntity user  = this.userService.fetch(id).orElseThrow(
            () -> new NotFoundException()
        );

        result.putPayload("user", user);

        return result;
    }

}
