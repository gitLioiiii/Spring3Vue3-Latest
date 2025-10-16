package com.example.demo.service.implementation;

import org.springframework.stereotype.Service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity login(String username, String password) {
        return userMapper.login(username, password);
    }
}
