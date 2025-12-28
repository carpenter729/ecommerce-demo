package com.example.ecommerce.service;

import com.example.ecommerce.entity.*;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService; // 需要调用购物车服务获取数据
    private final UserRepository userRepository;
    @Getter
    private final EmailService emailService;

    public OrderService(OrderRepository orderRepository, CartService cartService, UserRepository userRepository, EmailService emailService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // 核心业务：下单
    @Transactional // 务必加事务：保证生成订单和清空购物车要么都成功，要么都失败
    public void createOrder(String username, String address, String payment) {
        User user = userRepository.findByUsername(username).orElseThrow();
        List<CartItem> cartItems = cartService.getCartByUser(username);

        // 1. 获取购物车商品
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车为空");
        }

        Order order = new Order();
        order.setUser(user);
        order.setCreateTime(LocalDateTime.now());
        order.setShippingAddress(address);
        order.setPaymentMethod(payment);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // 3. 遍历购物车，转换为订单详情
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order); // 关联订单
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice()); // 记录购买时的价格

            orderItems.add(orderItem);

            // 计算总价：单价 * 数量
            BigDecimal itemTotal = item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()));
            total = total.add(itemTotal);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(total);

        // 4. 保存订单 (Cascade会自动保存 OrderItem)
        orderRepository.save(order);

        // 5. 清空购物车
        cartService.clearCart(username);
        // 6.发送确认邮件
        String emailContent = "尊敬的 " + username + "，您的订单已提交成功！\n" +
                "订单号: " + order.getId() + "\n" +
                "总金额: " + order.getTotalAmount();
        // 异步发送更好，为了简单直接同步发
        emailService.sendOrderConfirmation(user.getEmail(), "订单确认通知", emailContent);
    }

    // 查询用户的所有订单
    public List<Order> findUserOrders(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return orderRepository.findByUserOrderByCreateTimeDesc(user);
    }
    // 管理员查询所有订单
    public List<Order> findAllOrders() {
        return orderRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createTime"));
    }
    public void createOrder(String name) {
    }

}