package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(
        HttpSecurity httpSecurity
    ) throws Exception {

        httpSecurity.cors(cors -> {
            CorsConfiguration config = new CorsConfiguration();
            config.applyPermitDefaultValues();

            UrlBasedCorsConfigurationSource source  = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);

            cors.configurationSource(source);
        }).csrf(csrf -> {
            csrf.disable();
        }).authorizeHttpRequests(authorize -> {
            authorize.anyRequest().permitAll();
        });

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
