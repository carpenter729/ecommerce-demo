package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 多对一：一个用户可以有很多购物车条目
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 多对一：很多购物车条目可以是同一个商品
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity; // 购买数量
}