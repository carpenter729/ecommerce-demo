package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
public class OrderItem {
    // --- 手动 Getter/Setter ---
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // 归属哪个订单

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product; // 买了哪个商品

    private Integer quantity; // 买了几个
    private BigDecimal price; // 购买时的单价 (重要！要存快照，防止商品以后涨价)

}