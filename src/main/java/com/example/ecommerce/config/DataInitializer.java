package com.example.ecommerce.config;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final ProductService productService;

    public DataInitializer(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. 初始化测试用户
        if (userService.findByUsername("test") == null) {
            userService.saveUser("test", "123456", "test@example.com", "ROLE_USER");
            System.out.println(">>> 初始化测试用户: test / 123456");
            userService.createAdmin();
        }

        // 2. 初始化商品数据
        if (productService.findAll().isEmpty()) {
            createProduct("iPhone 15", "苹果最新手机", new BigDecimal("5999.00"), "/images/iphone.jpg", "数码");
            createProduct("MacBook Pro", "M3 芯片笔记本", new BigDecimal("12999.00"), "/images/macbook.jpg", "数码");
            createProduct("Sony WH-1000XM5", "降噪耳机", new BigDecimal("1999.00"), "/images/sony.jpg", "数码");
            createProduct("纯棉T恤", "夏季新款舒适透气", new BigDecimal("99.00"), "/images/tshirt.jpg", "服装");
            createProduct("足球", "好踢耐用", new BigDecimal("129.00"), "/images/football.jpg", "体育");
            createProduct("百年孤独", "经典书籍永不过时", new BigDecimal("59.00"), "/images/lonely.jpg", "书籍");
            System.out.println(">>> 初始化 6 个测试商品数据 (含分类)");
        }
    }

    // 方法签名增加了 category 参数
    private void createProduct(String name, String desc, BigDecimal price, String img, String category) {
        Product p = new Product();
        p.setName(name);
        p.setDescription(desc);
        p.setPrice(price);
        p.setImageUrl(img);
        p.setCategory(category); // 设置分类
        productService.saveProduct(p);
    }
}