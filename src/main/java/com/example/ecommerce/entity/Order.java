package com.example.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "orders") // order 是 SQL 关键字，必须改名
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createTime; // 下单时间
    private BigDecimal totalAmount;   // 订单总金额

    @Column(nullable = false)
    private String shippingAddress; // 收货地址

    @Column(nullable = false)
    private String paymentMethod;   // 支付方式 (例如：支付宝、微信、信用卡)

    // 一对多：一个订单包含多个商品详情
    // CascadeType.ALL 意味着：保存订单时，自动保存里面的所有详情
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

}