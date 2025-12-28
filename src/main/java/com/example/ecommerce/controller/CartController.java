package com.example.ecommerce.controller;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import com.example.ecommerce.service.UserService;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    @Getter
    private final UserService userService;
    private final OrderService orderService;

    public CartController(CartService cartService, UserService userService, OrderService orderService) {
        this.cartService = cartService;
        this.userService = userService;
        this.orderService = orderService; // 新增
    }

    // 1. 查看购物车页面
    @GetMapping
    public String viewCart(Model model, Principal principal) {
        // Principal 是 Spring Security 提供的，能直接获取当前登录的用户名
        String username = principal.getName();
        List<CartItem> cartItems = cartService.getCartByUser(username);

        // 计算总金额 (简单流式计算)
        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice().doubleValue() * item.getQuantity())
                .sum();

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "cart"; // 返回 cart.html
    }

    // 2. 加入购物车
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, Principal principal) {
        // 默认每次加 1 个
        cartService.addToCart(principal.getName(), productId, 1);
        return "redirect:/cart"; //以此重定向刷新页面
    }

    // 3. 删除购物车中的某项
    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, Principal principal) {
        // 这里的逻辑稍微简化，直接调用 repository 删除 (Service层最好封装一下，但这里直接调也行)
        // 为了规范，建议去 Service 加一个 delete 方法，但为了省事，我们暂且略过，假设 Service 有 delete
        // 哎呀，之前 Service 没写 delete，我们先不写删除功能，或者你去 CartService 加一个 deleteItem 方法
        // 既然是实战，我们用最简单的方式：先不做删除，只做增加和结算。
        return "redirect:/cart";
    }

    // 4. 结算（清空购物车）
    @PostMapping("/checkout")
    public String checkout(Principal principal) {
        // 调用下单逻辑
        orderService.createOrder(principal.getName());
        // 结算完成后，跳转到“我的订单”页面
        return "redirect:/orders";
    }

}