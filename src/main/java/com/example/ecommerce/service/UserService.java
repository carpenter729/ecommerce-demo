package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean registerUser(String username, String password,String email) {
        // 1. 检查用户名是否已存在
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // 用户名已存在，注册失败
        }

        // 2. 创建新用户
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // 记得加密！
        newUser.setEmail(email);
        newUser.setRole("ROLE_USER"); // 默认注册的都是普通用户
        userRepository.save(newUser);
        return true; // 注册成功
    }

    // 创建管理员
    public void createAdmin() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // 密码 admin123
            admin.setEmail("admin@ecommerce.com");
            admin.setRole("ROLE_ADMIN"); // 关键：角色是 ADMIN
            userRepository.save(admin);
            System.out.println(">>> 初始化管理员: admin / admin123");
        }
    }

    // 构造函数注入 (Spring 推荐写法，省去 @Autowired)
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 保存用户 (注册时用，密码要加密)
    public void saveUser(String username, String password,String email, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 关键：密码加密存储
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);
    }



    // 查找用户
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    // 核心：Spring Security 调用这个方法来校验登录
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // 将我们的 User 转换为 Spring Security 懂的 UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", "")) // Spring Security 自动加前缀，这里去掉
                .build();
    }
}