package com.example.ecommerce.controller;

import com.example.ecommerce.service.CartService;
import com.example.ecommerce.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService; // 必须加这个，用来计算总价

    // 构造函数：注入两个 Service
    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    // 1. 查看历史订单页面
    @GetMapping("/orders")
    public String myOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderService.findUserOrders(principal.getName()));
        return "my-orders";
    }

    // 2. 结算页面 (展示表单) —— 你刚才缺的就是这个！
    @GetMapping("/checkout")
    public String checkoutPage(Model model, Principal principal) {
        String username = principal.getName();
        // 计算购物车总金额，传给页面显示
        var cartItems = cartService.getCartByUser(username);
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // 如果购物车空的，不准结账
        }

        double total = cartItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice().doubleValue() * item.getQuantity())
                .sum();

        model.addAttribute("total", total);
        return "checkout"; // 返回 checkout.html
    }

    // 3. 处理下单请求 (提交表单) —— 你刚才也缺这个！
    @PostMapping("/order/create")
    public String createOrder(@RequestParam String address,
                              @RequestParam String paymentMethod,
                              Principal principal) {
        // 调用 Service 生成订单
        orderService.createOrder(principal.getName(), address, paymentMethod);
        return "redirect:/orders"; // 下单成功跳到订单列表
    }
}