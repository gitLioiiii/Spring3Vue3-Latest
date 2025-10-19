package com.example.demo.service.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.TokenEntity;
import com.example.demo.mapper.UserTokensMapper;
import com.example.demo.service.UserTokensService;

@Service
public class UserTokensServiceImpl implements UserTokensService {

    private final UserTokensMapper userTokensMapper;

    public UserTokensServiceImpl(UserTokensMapper userTokensMapper) {
        this.userTokensMapper   = userTokensMapper;
    }

    public Optional<TokenEntity> fetch(String token) {
        return Optional.ofNullable(
            this.userTokensMapper.find(token)
        );
    }

    public Integer create(TokenEntity token) {
        return this.userTokensMapper.create(token);
    }

}
