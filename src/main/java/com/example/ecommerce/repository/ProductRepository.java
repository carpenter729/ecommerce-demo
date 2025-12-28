package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // 1. 查询所有商品 + 分页 (JPA自带 findAll(Pageable)，不用写)
    // 2. 根据分类查询 + 分页
    // Spring Data JPA 会自动翻译成 SQL: SELECT * FROM product WHERE category = ? LIMIT ?, ?
    Page<Product> findByCategory(String category, Pageable pageable);
}