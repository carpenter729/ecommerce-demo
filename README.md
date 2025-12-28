# Spring Boot 全栈电商系统 (Ecommerce Demo)

[![Java](https://img.shields.io/badge/Java-17-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-green)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue)](https://www.docker.com/)

这是一个基于 Spring Boot 3 和 Docker 构建的现代化轻量级电商平台。项目采用前后端一体化架构（Thymeleaf），实现了完整的购物流程、后台管理以及邮件通知服务。

##  核心功能

###  用户端 (Shopping)
*   **用户认证**：注册、登录、注销（基于 Spring Security + BCrypt 加密）。
*   **商品浏览**：
    *   支持按分类筛选（数码、服装等）。
    *   支持分页显示商品列表。
*   **购物车**：添加商品、查看购物车详情、计算总价。
*   **订单流程**：填写收货地址、模拟支付、生成订单。
*   **邮件通知**：下单成功后自动发送确认邮件（集成 MailHog 进行拦截测试）。

###  管理端 (Admin Dashboard)
*   **商品管理**：发布新商品（支持设置名称、价格、分类、图片链接）、删除商品。
*   **订单查看**：管理员可查看全平台所有用户的历史订单数据。
*   **权限控制**：基于角色的访问控制 (RBAC)，普通用户无法访问后台接口。

---

##  技术栈

*   **后端**: Java 17, Spring Boot 3.x, Spring Security, Spring Data JPA
*   **数据库**: MySQL 8.0
*   **前端**: Thymeleaf 模板引擎, Bootstrap 5 UI 框架
*   **基础设施**: Docker, Docker Compose
*   **测试工具**: MailHog (SMTP 测试服务)

---

##  快速开始 (如何运行)

本项目支持 Docker 一键部署，这是最推荐的运行方式，无需在本地安装 Java 或 MySQL 环境。

### 前置要求
*   已安装 [Docker](https://www.docker.com/products/docker-desktop) 和 Docker Compose。
*   (可选) [Git](https://git-scm.com/) 用于克隆项目。

### 步骤 1：克隆项目
```bash
git clone https://github.com/carpenter729/ecommerce-demo.git
cd ecommerce-demo
步骤 2：启动服务
在项目根目录下运行以下命令，系统会自动构建镜像并启动 App、MySQL 和 MailHog：
code
Bash
docker-compose up -d --build
(首次运行可能需要几分钟下载基础镜像，请耐心等待)
步骤 3：访问应用
启动完成后，通过浏览器访问以下地址：
 商城主页: http://localhost:8080
 邮件测试台: http://localhost:8025
注意：如果是部署在云服务器上，请将 localhost 替换为服务器的公网 IP，并确保防火墙已开放 8080 和 8025 端口。
 功能体验指南 (测试流程)
为了帮助您快速体验所有功能，请按照以下剧本操作：
场景一：普通用户购物流程
注册/登录
访问首页，点击右上角“登录”。
使用预置测试账号：test / 123456，或者点击“注册”创建一个新账号。
浏览商品
在首页尝试点击“数码产品”或“潮流服装”进行分类切换。
尝试点击底部的“下一页”体验分页功能。
购物结算
将几个商品加入购物车。
进入购物车页面，点击“去结算”。
随便填写地址和支付方式，点击提交。
查看邮件
下单成功后，浏览器新开一个标签页访问 http://localhost:8025。
您应该能看到刚刚发送的“订单确认通知”邮件，里面包含订单号和金额。
场景二：管理员后台管理
管理员登录
先注销当前用户。
登录管理员账号（系统预置）：
用户名：admin
密码：admin123
添加商品
登录后，访问 http://localhost:8080/admin/products。
点击“添加新商品”。
输入信息（图片路径可填 /images/iphone.jpg 或网络图片链接），选择分类（如“服装”）。
保存后，回到前台首页，验证新商品是否出现。
查看所有订单
在后台点击“查看订单”，可以看到刚才普通用户下的订单详情。

📂 项目结构说明

ecommerce-demo/
├── src/main/java/com/example/ecommerce
│   ├── config/          # 安全配置与数据初始化
│   ├── controller/      # Web 控制器 (处理 HTTP 请求)
│   ├── entity/          # 数据库实体类 (User, Product, Order...)
│   ├── repository/      # 数据访问层 (JPA 接口)
│   └── service/         # 业务逻辑层
├── src/main/resources
│   ├── static/images/   # 静态图片资源
│   ├── templates/       # Thymeleaf HTML 页面
│   └── application.properties # 项目配置
├── docker-compose.yml   # 容器编排文件
├── Dockerfile           # 构建镜像描述
└── mysql-data/          # (自动生成) 数据库持久化文件

 常见问题
Q: 启动后访问报错 "Connection refused" 或 502？
A: MySQL 第一次初始化需要时间。请等待 30 秒左右，再次刷新网页即可。
Q: 图片无法显示？
A: 确保添加商品时填写的路径是 /images/文件名.jpg，且该文件真实存在于 src/main/resources/static/images/ 目录下。
Q: 修改代码后如何更新？
A: 重新运行 docker-compose up -d --build，Docker 会检测文件变化并重新打包镜像。
