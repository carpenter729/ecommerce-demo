package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1. 定义密码加密器 (解决 UserService 里的报错)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. 定义安全过滤器链 (核心配置)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 配置请求权限
                .authorizeHttpRequests(auth -> auth
                        // 允许所有人访问：首页、登录页、注册页、静态资源(css/js/images)
                        .requestMatchers("/", "/home", "/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                        // 后台管理接口，只有 ADMIN 角色能访问
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // 其他所有请求（比如购物车、下单）都需要登录后才能访问
                        .anyRequest().authenticated()
                )
                // 配置表单登录
                .formLogin(form -> form
                        .loginPage("/login") // 自定义登录页面的 URL (稍后我们在 Controller 里写)
                        .defaultSuccessUrl("/", true) // 登录成功后跳转到首页
                        .permitAll()
                )
                // 配置注销
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout") // 注销成功跳回登录页
                        .permitAll()
                );

        return http.build();
    }
}