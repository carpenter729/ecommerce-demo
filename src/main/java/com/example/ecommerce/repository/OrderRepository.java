package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 查询某用户的历史订单，按时间倒序排列（最新的在前面）
    List<Order> findByUserOrderByCreateTimeDesc(User user);
}