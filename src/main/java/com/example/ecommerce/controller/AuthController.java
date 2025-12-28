package com.example.ecommerce.controller;

import com.example.ecommerce.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 1. 展示注册页面
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // 返回 register.html
    }

    // 2. 处理注册请求
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               RedirectAttributes redirectAttributes) {
        boolean success = userService.registerUser(username, password, email);

        if (success) {
            // 注册成功，跳到登录页，并显示成功提示
            redirectAttributes.addFlashAttribute("registerSuccess", "注册成功！请登录。");
            return "redirect:/login";
        } else {
            // 注册失败（用户名已存在），跳回注册页，并显示错误提示
            redirectAttributes.addFlashAttribute("registerError", "用户名已存在，请换一个。");
            return "redirect:/register";
        }
    }
}