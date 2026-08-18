# ==========================================
# 阶段 1: 构建阶段 (Build Stage)
# ==========================================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1. 先只复制 pom.xml，下载依赖 (利用 Docker 缓存层，加速后续构建)
COPY pom.xml .
RUN mvn dependency:go-offline

# 2. 复制源代码并打包
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# 阶段 2: 运行阶段 (Run Stage)
# ==========================================
# 使用轻量级的 JRE 镜像，而不是庞大的 JDK 镜像，减小体积
FROM eclipse-temurin:17-jre
WORKDIR /app

# 从 build 阶段复制打包好的 jar 文件，并重命名为 app.jar
# 如果你的 pom.xml 中配置了固定的 finalName，可以直接写死名字，否则用 *.jar
COPY --from=build /app/target/*.jar app.jar

# Render 会通过环境变量 $PORT 告诉你应该监听哪个端口 (通常是 10000)
ENV PORT=10000
EXPOSE $PORT

# 启动 Spring Boot 应用
# 关键点 1: --server.port=${PORT} 确保 Spring Boot 监听 Render 分配的端口
# 关键点 2: -Xmx256m 限制最大堆内存，防止在 Render 免费层 (512MB) 被 OOM Kill
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "app.jar", "--server.port=${PORT}"]