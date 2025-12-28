package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data // Lombok 自动生成 Get/Set/ToString
@Table(name = "users") // 数据库表名叫 users (user是SQL关键字，避开)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // 用户名

    @Column(nullable = false)
    private String password; // 密码

    @Setter
    @Getter
    @Column(nullable = false) // 暂时设为 nullable=true 也可以，为了兼容旧数据建议重置数据库
    private String email;

    private String role; // 角色 (ROLE_USER, ROLE_ADMIN)

}

