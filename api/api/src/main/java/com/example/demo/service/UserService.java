package com.example.demo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.entity.UserEntity;

public interface UserService {
    
    // UserEntity login(String username, String password);

    List<UserEntity> fetch(Map<String, Object> filter);

    // UserEntity fetch(Integer id);

    Integer create(UserEntity user);

    Integer update(UserEntity user);

    Integer remove(UserEntity user);


    public Integer count(Map<String, Object> filter);

    Optional<UserEntity> fetch(Integer id);

    Optional<UserEntity> fetch(String username);

}
