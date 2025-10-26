package com.example.demo.service;

import java.util.Optional;

import com.example.demo.entity.TokenEntity;

public interface UserTokensService {

    public Optional<TokenEntity> fetch(String token);

    public Integer create(TokenEntity tokenEntity);

}
