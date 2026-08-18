# Personal-Blog-Backstage 个人博客后台服务

> 一个基于 **Spring Boot 3** 的个人博客后端服务，为前端个人博客站点（如 blog.insand.top）提供完整的 REST API、实时聊天室、第三方登录、对象存储上传与 Redis 缓存等能力。

---

## 一、项目是干什么的？

本项目是个人博客系统的**后端服务（Backstage）**，负责为博客前端提供数据接口与业务支撑，主要包含：

- **文章模块**：文章的增删改查、按分类分页查询、随机文章、作者文章分页、标题搜索、文章数量统计等；
- **内容互动**：评论、点赞、收藏、关注、留言板、留言、浏览历史、发布消息等；
- **社区与个人**：用户管理、个人资料（Profiles）、友链、标签、专辑/合辑（Compilations）、时光穿梭（TimeShuttle）、艺术家（Artist）等；
- **账号体系**：账号密码登录、注册、Gitee 第三方 OAuth 登录（`/giteeLogin`）、第三方账号绑定；
- **实时聊天室**：基于 WebSocket（`/socket`）的多人实时消息广播；
- **文件上传**：基于腾讯云 COS 的对象存储上传（图片、音频等）；
- **缓存与性能**：Redis + Redisson 缓存热点数据（文章列表等）。

一句话总结：**它是个人博客站的“大脑”，前端页面展示的所有数据都来自这里，并负责登录鉴权、实时通信与文件存储。**

---

## 二、技术栈

| 分类 | 技术 |
| --- | --- |
| 语言 | Java 17 |
| 框架 | Spring Boot 3.2.5、Spring MVC |
| 持久层 | MyBatis-Plus 3.5.7 + MyBatis XML 映射 |
| 数据库 | MySQL（当前配置为 TiDB Cloud 兼容 MySQL 协议） |
| 缓存 | Redis（spring-data-redis-reactive）+ Redisson + Spring Cache |
| 实时通信 | Spring WebSocket（文本消息广播） |
| 认证 | JWT（jjwt 0.9.1）+ 自定义拦截器 `MyInterceptor` |
| 第三方登录 | Gitee OAuth2（授权码模式） |
| 对象存储 | 腾讯云 COS（cos_api 5.6.227） |
| HTTP 客户端 | WebFlux WebClient、Hutool、OkHttp |
| 工具库 | Lombok、Fastjson、commons-codec、commons-lang3 |
| 代码生成 | MyBatis-Plus Generator + Freemarker 自定义模板（`CodeNew`） |
| 构建部署 | Maven、Docker（OpenJDK 17 镜像） |

---

## 三、服务架构

项目采用经典的 **分层架构（Controller → Service → Mapper → 数据库）**，并在此基础上扩展了跨切面能力（拦截器、异常处理）与外部服务集成。

```
                         ┌─────────────────────────────────────────┐
                         │           前端 / 外部客户端                │
                         │   (博客前端页面 / App / 调试工具)          │
                         └───────────────────┬─────────────────────┘
                                             │ HTTP / WebSocket
                                             ▼
                         ┌─────────────────────────────────────────┐
                         │         Spring Boot 3 应用 (8080)        │
                         │                                         │
                         │  ┌───────────────────────────────────┐  │
                         │  │    MyInterceptor (JWT 鉴权拦截器)    │  │
                         │  │  GET 直接放行 / 其他请求校验 token   │  │
                         │  └───────────────────┬───────────────┘  │
                         │                      ▼                  │
                         │  ┌───────────────────────────────────┐  │
                         │  │   Controller 控制层 (REST API)      │  │
                         │  │  /article /users /chatroom ...    │  │
                         │  └───────────────────┬───────────────┘  │
                         │                      ▼                  │
                         │  ┌───────────────────────────────────┐  │
                         │  │   Service 业务层 (接口 + Impl)      │  │
                         │  └───────────────────┬───────────────┘  │
                         │                      ▼                  │
                         │  ┌───────────────────────────────────┐  │
                         │  │   Mapper 持久层 (MyBatis-Plus)     │  │
                         │  │   + XML 映射文件                    │  │
                         │  └───────────────────┬───────────────┘  │
                         │                      │                 │
                         │    ┌─────────────────┼─────────────┐   │
                         │    ▼                 ▼             ▼   │
                         │  MySQL / TiDB      Redis         COS   │
                         │  (业务数据)      (缓存/分布式锁)  (文件) │
                         └─────────────────────────────────────────┘
                                             │
                                             ▼
                         ┌─────────────────────────────────────────┐
                         │  Gitee OAuth2 (第三方登录)               │
                         │  WebSocket (/socket) 实时聊天室           │
                         └─────────────────────────────────────────┘
```

### 3.1 各层职责

| 层次 | 说明 |
| --- | --- |
| **Interceptor 拦截器层** | `MyInterceptor` 统一处理登录鉴权：**GET 请求直接放行，其他请求（POST/PUT/DELETE）必须携带有效 JWT**；`/Login/**`、`/giteeLogin/**` 路径已在 `WebConfig` 中排除拦截 |
| **Controller 控制层** | 暴露 REST 接口，接收请求、调用 Service、统一返回 `Result` 结构体 |
| **Service 业务层** | 接口（`IxxxService`）+ 实现（`xxxServiceImpl`），承载业务逻辑，如分页组装、Redis 缓存读写 |
| **Mapper 持久层** | MyBatis-Plus `BaseMapper` 接口 + 对应 XML 文件，负责 SQL 与数据库交互 |
| **POJO 实体层** | 数据库表对应的实体类，以及 `Result`（统一响应）、`RestBean` 等封装对象 |

### 3.2 横切与集成组件

| 组件 | 位置 | 作用 |
| --- | --- | --- |
| `MyInterceptor` | `interceptor/` | 登录鉴权拦截器（JWT 校验） |
| `GlobalExceptionHandler` | `exception/` | 全局异常处理，统一错误响应 |
| `WebConfig` / `CorsConfig` | `untill/` | 拦截器注册 + 跨域配置 |
| `JwtUtils` | `untill/` | JWT 生成与解析（HS256） |
| `SHA256Util` / `KaisaUtil` | `untill/` | 密码/数据加密工具 |
| `RedisServe` + `RedisConfig` | `redis/` | Redis 缓存读写封装（含过期时间、List 操作） |
| `MyWebSocketHandler` | `webSocet/` | WebSocket 消息广播（聊天室） |
| `COSUtil` + `UploadFileController` | `untill/CosConfig/` | 腾讯云 COS 文件上传 |
| `UploadController` / `DelectControllerFile` | `untill/RichTextConfiguration/` | 富文本图片上传与删除 |
| `CodeNew` | 根包 | MyBatis-Plus 代码生成器（Freemarker 模板） |

### 3.3 请求处理流程

```
客户端请求
   │
   ▼
CORS 跨域过滤
   │
   ▼
MyInterceptor.preHandle
   ├── 是 GET 请求？──────────────► 直接放行
   └── 非 GET 请求
        ├── token 为空 ──────────► 返回 Result.error("No_LOGIN")
        ├── token 为内置放行令牌 ──► 直接放行
        └── JwtUtils.parseJWT 解析
              ├── 解析成功 ──────► 放行进入 Controller
              └── 解析失败 ──────► 返回 Result.error("No_LOGIN")
   │
   ▼
Controller → Service → Mapper → MySQL / Redis
   │
   ▼
统一 Result 响应返回前端
```

---

## 四、接口模块一览

所有接口统一返回 `Result` 结构：`{ "code": 1成功/0失败, "msg": 描述, "data": 数据 }`。

| 前缀 | 模块 |
| --- | --- |
| `/Login` | 登录、注册、账户更新（已排除拦截） |
| `/giteeLogin` | Gitee 第三方 OAuth 登录（已排除拦截） |
| `/article` | 文章（分页/分类/随机/搜索/增删改） |
| `/articleCategories` | 文章分类 |
| `/comments` | 评论 |
| `/praise` | 点赞 |
| `/collections` | 收藏 |
| `/follows` | 关注 |
| `/leaveWords` | 留言 |
| `/messageBoard` | 留言板 |
| `/historybrowsing` | 浏览历史 |
| `/users` | 用户管理 |
| `/userThirdAuth` | 用户第三方认证绑定 |
| `/profiles` | 个人资料 |
| `/friendlink` | 友情链接 |
| `/tags` | 标签 |
| `/compilations` | 合辑/专辑 |
| `/artist` | 艺术家 |
| `/timeShuttle` | 时光穿梭 |
| `/publishmessages` | 发布消息 |
| `/chatroom` | 聊天室（配合 WebSocket `/socket`） |
| `/webview` | 网页浏览数据 |

---

## 五、鉴权说明（拦截器规则）

`MyInterceptor` 拦截所有路径（`/**`），除 `/Login/**` 与 `/giteeLogin/**` 外：

1. **GET 请求直接放行** —— 查询类操作无需登录即可访问；
2. **其他请求（POST / PUT / DELETE 等）必须携带有效 JWT**：
   - 请求头缺少 `token` → 返回 `{"code":0,"msg":"No_LOGIN"}`；
   - `token` 等于内置放行令牌 → 直接通过；
   - `token` 无法通过 `JwtUtils.parseJWT` 解析 → 返回 `{"code":0,"msg":"No_LOGIN"}`。

登录成功后由 `/Login` 返回 `token`，前端在后续写操作请求头中携带 `token: <jwt>` 即可。

---

## 六、配置说明

核心配置见 `src/main/resources/application.properties`：

| 配置项 | 说明 |
| --- | --- |
| `spring.datasource.*` | MySQL/TiDB 数据库连接（驱动、URL、账号密码） |
| `server.port` | 服务端口（默认 8080） |
| `spring.servlet.multipart.*` | 上传文件大小限制（100MB） |
| `upload.path` | 音频上传本地路径 |
| `tencent.cos.*` | 腾讯云 COS 区域、桶、密钥 |
| `client_id / client_secret / redirect_uri` | Gitee OAuth 应用凭证 |
| `spring.data.redis.*` | Redis 连接（host/port/password） |
| `spring.redis.redisson.config` | Redisson 配置文件路径 |

> ⚠️ 注意：`application.properties` 中包含数据库、COS、Gitee 等敏感凭证，生产环境请通过环境变量或密钥管理服务注入，切勿提交到公开仓库。

---

## 七、构建与部署

### 本地运行

```bash
# 打包
mvn clean package -DskipTests

# 运行
java -jar target/SpringBootText-0.0.1-SNAPSHOT.jar
```

### Docker 部署

项目根目录提供 `Dockerfile`（基于 `openjdk:17-jdk-alpine`，暴露 8081 端口）：

```bash
docker build -t personal-blog-backstage .
docker run -d -p 8081:8081 personal-blog-backstage
```

---

## 八、代码生成

`CodeNew.java` 内置 MyBatis-Plus 代码生成器，修改 `tables` 列表中的表名后运行 `main` 方法，即可自动生成对应实体类、Mapper、Service、ServiceImpl、Controller 及 XML 映射文件（模板位于 `src/main/resources/templates/*.ftl`）。
