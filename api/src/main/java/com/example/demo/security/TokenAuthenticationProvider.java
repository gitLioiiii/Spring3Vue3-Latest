package com.example.demo.security;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.example.demo.entity.TokenEntity;
import com.example.demo.service.UserTokensService;

public class TokenAuthenticationProvider implements AuthenticationProvider {

    private final ApplicationContext applicationContext;

    public TokenAuthenticationProvider(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 检查Token是否存在
        Optional.ofNullable(authentication.getPrincipal()).orElseThrow(
            () -> new TokenAuthenticationException()
        );
        // 从数据库查找Token
        TokenEntity token   = this.retrieveToken((String) (authentication.getPrincipal())).orElseThrow(
            () -> new TokenAuthenticationException()
        );
        // 检查Token是否过期
        if (token.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new TokenAuthenticationException();
        }
        // 检查Token是否关联用户
        Optional.ofNullable(token.getUser()).orElseThrow(
            () -> new TokenAuthenticationException()
        );
        
        // 返回认证成功的Authentication对象
        TokenAuthentication tokenAuthentication = new TokenAuthentication(token.getUser(), List.of());
        tokenAuthentication.setDetails(authentication.getDetails());
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return TokenAuthentication.class.isAssignableFrom(authentication);
    }

    protected Optional<TokenEntity> retrieveToken(String token) {
        UserTokensService userTokenService	= this.applicationContext.getBean(UserTokensService.class);
        return userTokenService.fetch(token);
    }

}
