package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Product;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.ProductService;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    @Getter
    private final OrderService orderService;

    public AdminController(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;
    }

    // 后台首页：商品列表管理
    @GetMapping("/products")
    public String productList(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin-products"; // 返回 admin-products.html
    }

    // 添加商品页面
    @GetMapping("/products/add")
    public String addProductPage() {
        return "admin-product-add"; // 返回 admin-product-add.html
    }

    // 管理员查看所有订单页面
    @GetMapping("/orders")
    public String adminOrders(Model model) {
        model.addAttribute("orders", orderService.findAllOrders());
        return "admin-orders"; // 返回 admin-orders.html
    }

    // 处理添加请求
    @PostMapping("/products/add")
    public String addProduct(@RequestParam String name,
                             @RequestParam String description,
                             @RequestParam BigDecimal price,
                             @RequestParam String imageUrl,
                             @RequestParam String category){
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        product.setCategory(category);
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    //  删除商品
    @PostMapping("/products/delete")
    public String deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }


}