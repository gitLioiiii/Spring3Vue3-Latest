package com.example.demo.service.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<UserEntity> fetch(Map<String, Object> filter) {
        return this.userMapper.find(filter);
    }

    @Override
    public UserEntity fetch(Integer id) {
        return this.userMapper.findById(id);
    }

    @Override
    public Integer create(UserEntity user) {
        return this.userMapper.create(user);
    }

    @Override
    public Integer update(UserEntity user) {
        return this.userMapper.update(user);
    }

    @Override
    public Integer remove(UserEntity user) {
        user.setDeletedAt(LocalDateTime.now());

        return this.userMapper.remove(user);
    }
}
