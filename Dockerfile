# 使用 OpenJDK 作为基础镜像，这里使用的是 Java 11 的 OpenJDK 镜像
FROM openjdk:17

# 将工作目录设置为 /app
WORKDIR /app

# 将本地的 JAR 文件复制到容器的 /app 目录下
COPY target/campus-lost-search-0.0.1-SNAPSHOT.jar /app/app.jar

# 暴露应用运行的端口，假设 Spring Boot 应用运行在 8080 端口，可根据实际情况修改
EXPOSE 8080

# 定义容器启动时执行的命令
CMD ["java", "-jar", "app.jar"]