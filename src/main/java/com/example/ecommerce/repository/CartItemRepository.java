package com.example.ecommerce.repository;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 查询某用户的购物车所有商品
    List<CartItem> findByUser(User user);

    // 查询某用户购物车里是否已经有某个商品（为了做数量+1逻辑）
    Optional<CartItem> findByUserAndProduct(User user, Product product);
}