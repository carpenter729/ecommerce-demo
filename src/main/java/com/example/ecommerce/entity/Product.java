package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // 商品名

    private String description; // 描述

    @Column(nullable = false)
    private BigDecimal price; // 价格 (涉及金钱一定要用 BigDecimal)

    private String imageUrl; // 图片链接 (先存字符串，比如 "iphone.jpg")

    private String category; //商品分类
}