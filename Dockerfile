# 基础镜像：我们需要一个装有 Java 23 的环境
FROM eclipse-temurin:23-jdk

# 作者信息
LABEL maintainer="邱岳林"

# 创建一个临时卷 (用于 Tomcat 临时文件)
VOLUME /tmp

# 把我们刚才打好的 jar 包复制进容器，并改名为 app.jar
# 注意：如果你的 target 下的文件名不一样，请修改这里
COPY ecommerce-demo-0.0.1-SNAPSHOT.jar app.jar

# 暴露端口 (告诉 Docker 这个容器用8080)
EXPOSE 8080

# 启动命令：相当于在终端输入 java -jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]