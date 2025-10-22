package com.example.demo.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity(debug = true)
// (debug = true)作用: 开启 Spring Security 的调试模式。
// 打印过滤器链: 在日志中详细输出每个请求经过了哪些 Security 过滤器、哪些被跳过。
// 匹配结果可见: 显示 antMatcher/requestMatcher 的匹配与否、授权决策、是否需要认证等。
// 上下文细节: 可能包含 SecurityContext、会话、CSRF 等调试信息。
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity
    ) throws Exception {

        // 添加token认证管理器
        AuthenticationManager authenticationManager = new ProviderManager(
            new TokenAuthenticationProvider(
                httpSecurity.getSharedObject(ApplicationContext.class)
            )
        );
    // 添加token过滤器
        httpSecurity.authenticationManager(
            authenticationManager
        ).addFilterAfter(
            new TokenAuthenticationFilter(
                new RequestHeaderRequestMatcher("Authorization"),//匹配请求头中的Authorization字段
                authenticationManager
            ),
            CorsFilter.class
        ).sessionManagement(session -> {
            session.disable();//禁用session
        }).cors(cors -> {
            CorsConfiguration config = new CorsConfiguration();
            config.applyPermitDefaultValues();

            UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);

            cors.configurationSource(source);
        // httpSecurity.cors(cors -> {
        //     CorsConfiguration config = new CorsConfiguration();
        //     config.applyPermitDefaultValues();

        //     UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
        //     source.registerCorsConfiguration("/**", config);

        //     cors.configurationSource(source);
        }).csrf(csrf -> {
            csrf.disable();
        }).logout(logout -> {
            logout.disable(); // 禁用默认的logout配置
        }).authorizeHttpRequests(authorize -> {
            authorize.anyRequest().permitAll();//允许所有请求
        });

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
