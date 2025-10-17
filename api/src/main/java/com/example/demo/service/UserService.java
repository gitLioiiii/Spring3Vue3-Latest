package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.UserEntity;

public interface UserService {
    
    UserEntity login(String username, String password);

    List<UserEntity> fetch(Map<String, Object> filter);

    UserEntity fetch(Integer id);

    Integer create(UserEntity user);

    Integer update(UserEntity user);

    Integer remove(UserEntity user);
}
