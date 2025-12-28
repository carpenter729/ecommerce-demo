package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageController {

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    // 1. 首页：展示商品列表
    @GetMapping("/")
    public String home(Model model,
                       @RequestParam(defaultValue = "0") int page, // 默认第0页
                       @RequestParam(required = false) String category) { // 分类可选

        // 每页显示 6 个商品
        int pageSize = 6;

        Page<Product> productPage = productService.findProducts(page, pageSize, category);

        model.addAttribute("productPage", productPage);
        model.addAttribute("currentCategory", category); // 把当前选的分类传回去，用于翻页链接保持状态

        return "home";
    }

    // 2. 登录页
    @GetMapping("/login")
    public String login() {
        return "login"; // 返回 login.html
    }
}