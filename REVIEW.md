# 代码审查报告（Code Review Report）

> 审查范围：Personal-Blog-Backstage（Spring Boot 3.2.5 个人博客后端）
> 审查日期：2025 年
> 结论：**存在 3 个高危安全漏洞、多个功能性 bug、若干性能瓶颈，不建议直接上生产，需先修复下列高优先级项。**

---

## 一、不必要的代码（冗余 / 死代码）

| # | 位置 | 问题 |
| --- | --- | --- |
| 1 | `ArticleServiceImpl` | 同一 `ArticleMapper` 注入了两次：`articlemapper` 与 `articleMapper` 混用，应合并为一个 |
| 2 | `ArticleServiceImpl.getByid` | 整段 Redis 缓存逻辑被注释（约 20 行死代码），而 `deleteByid` 仍在清理一个从不写入的键 `key+id` |
| 3 | `UsersServiceImpl` | 多处 `catch (RuntimeException e) { throw new RuntimeException(e); }` 空包装，捕获后原样抛出，毫无意义 |
| 4 | `UploadController` / `DelectControllerFile` | 每次请求 `new UploadFileController()`，应注入 Bean；`uploadUrl` 字段、`deleteFile()` 方法、`getFileExtensionFromFileObject()` 均未被使用 |
| 5 | `UploadFileController` | `delateFile` 方法拼写错误且无人调用（DelectControllerFile 直接调 `COSUtil.deleteFile`） |
| 6 | `GiteeLoginController` | 每次请求 `new RestTemplate()`，但项目已在 `WebClientConfig` 定义 `RestTemplate` Bean 却不注入 |
| 7 | 命名 | 类 `AudioUploadUntill` 与包名 `untill` 拼写错误（util）；此类是 Controller 却放在 util 包 |
| 8 | `UsersMapper.xml` | 大段注释掉的 update SQL 死代码 |
| 9 | `application.properties` | `mybatis.mapper-locations: classpath:mapper/*.xml` 指向的目录不存在（XML 实际在 `classpath:com/example/springboottext/mapper/`，靠同包自动加载），配置无效；`upload.path` 定义了但从未被使用 |
| 10 | `pom.xml` | `fastjson` 声明两次（1.2.76 与 2.0.53）；`lombok` 声明两次；`mybatis-plus-extension` 单独引入冗余 |
| 11 | 生成式 Controller | `@Transactional` 大量加在 GET 只读接口上，无实际事务需求 |
| 12 | `ArticleMapper` | `getIdentityByid` 双分号 `;;`；`getRandomArticle` 外层 `SELECT DISTINCT * FROM (...)` 冗余包裹 |

---

## 二、重大 Bug

### 2.1 高危安全问题（必须优先修复）

**① 登录校验运算符优先级错误 + 明文密码比对（`UsersServiceImpl.Loginfind` L113）**
```java
if(users != null && SHA256Util.verifyPassword(...) || users.getPassword().equals(KaisaUtil.decryptKaiser(password)))
```
- Java 优先级为 `(A && B) || C`：只要**库里存的密码字符串恰好等于输入的明文**即登录成功（错误的后门）；
- 当账号不存在（`users == null`）时，`|| C` 分支仍会执行 `users.getPassword()` → **空指针异常**，登录失败直接 500，而不是返回"账号或密码错误"。
- 修复：`users != null && (verifyPassword(...) || legacyCheck(...))`。

**② 密码哈希泄露给前端（`Users.java`）**
`Users.password` 无 `@JsonIgnore`：
- `/users` 列表、`/users/{id}` 返回**所有用户含密码哈希**；
- `/Login` 登录响应把 user 对象（含哈希）整体返回；
- 结合上一轮改造"GET 全部免鉴权"，**任何人都可匿名拉取全站用户密码哈希** → 必须给 `password` 加 `@JsonIgnore` 并引入 DTO/VO。

**③ 敏感凭据硬编码并提交进仓库**
- `COSUtil`：`SECRET_ID` / `SECRET_KEY` / `BUCKET_NAME` 写死在 public static 字段；
- `application.properties`：MySQL（TiDB）账号密码、COS 密钥、Gitee client_secret、Redis 密码全部明文提交；
- `MyInterceptor`：硬编码一个固定 token 直接放行（后门）。
- 修复：全部改为环境变量 / 配置中心注入，轮换已泄露的密钥。

**④ “凯撒加密”传输密码 + 日志打印明文（`KaisaUtil` / `UsersServiceImpl`）**
- 固定偏移 1008611 的凯撒密码是幼儿园级"加密"，等于明文传输；
- `Loginfind` 中 `log.info("传递的明文？？{}", KaisaUtil.decryptKaiser(password))` **把明文密码写进日志**。
- 修复：前端 HTTPS + 后端 `BCrypt`/`Argon2` 哈希；日志一律脱敏。

**⑤ Gitee OAuth 回调无 state 防 CSRF（`GiteeLoginController`）**
- 只用 `code` 换取 token，没有 `state` 参数校验 → 可被 CSRF 攻击劫持登录态；
- `responses.getBody()` 未判空、`userInfo.get(...).toString()` 多处潜在 NPE；
- `insert()` 给第三方用户 `setPassword("123456")` 明文后走凯撒解密流程，逻辑混乱。

### 2.2 功能正确性 bug

| # | 位置 | 问题 |
| --- | --- | --- |
| 6 | `ArticleServiceImpl` | **缓存不失效（脏数据）**：`ArticleList` 与分页键缓存 200s，但 insert/update/delete 只清理从不写入的 `key+id` → 增删改文章后列表 200s 内不更新 |
| 7 | `ArticleServiceImpl.getPaginatedList` | 缓存键 `"Article"+pages+index` 无分隔符 → `(pages=21,index=1)` 与 `(pages=2,index=11)` 键冲突 |
| 8 | `UsersServiceImpl` | 用户列表/详情缓存**永不过期**，且 update/delete 不失效 → 永远脏数据 |
| 9 | `UsersController.deleteByid` | `COSUtil.deleteFile(users.getProfilephoto())` 传的是**完整 URL**，而 deleteFile 需要 **object key** → 头像永远删不掉 |
| 10 | `AudioUploadUntill` | 硬编码 `D:\Study_Note\...` 绝对路径 → Docker/Linux 部署必失败；`fileName` 直接拼路径 → **路径穿越漏洞** |
| 11 | `UploadFileController.uploadFile` | 文件名无 `.` 时 `substring(-1)` → `StringIndexOutOfBoundsException`；无扩展名白名单 → 任意文件上传公开 COS |
| 12 | `COSUtil.upLoad` | 键用毫秒时间戳，同一毫秒上传互相覆盖；每次 `new COSClient` 从不 close（连接泄漏）；返回 `http://` |
| 13 | `DelectControllerFile` | 硬编码 `http://` URL 前缀，换域名/https 即抛异常 |
| 14 | `application.properties` | `spring.redis.redisson.config=classpath:redisson-single-node.yml` 但**该文件不存在** → Redisson 启动失败风险 |
| 15 | `Dockerfile` | `COPY target/your-springboot-app.jar` 是占位名（实际 `SpringBootText-0.0.1-SNAPSHOT.jar`）→ docker build 必失败；`EXPOSE 8081` 与 `server.port=8080` 不一致 |
| 16 | `pom.xml` | fastjson 1.x 与 2.x 依赖同时声明、代码 `com.alibaba.fastjson` / `com.alibaba.fastjson2` 混用 → 构建/运行 NoClassDefFoundError 风险 |
| 17 | `UsersController.getOpenTypeByid` | `@GetMapping` 配 `@RequestBody`，GET 带 body 不规范，代理/客户端可能丢 body |
| 18 | 点赞/关注/收藏 | insert 前无唯一性校验（check-then-insert 竞态）+ 表无唯一约束 → 并发下重复数据；`UsersMapper.Loginfind` 用 `username=#{} OR email=#{}` 两字段同值歧义 |

### 2.3 性能 bug

| # | 位置 | 问题 |
| --- | --- | --- |
| 19 | `ArticleMapper.getRandomArticle` | `ORDER BY RAND()` 每次全表扫描+排序，高流量下是灾难 |
| 20 | `ArticleMapper.getArticleByTitle` | `LIKE '%kw%'` 前导通配符无法走索引，全表扫描 |
| 21 | `ArticleMapper.getList` / `getIdentityByid` / `getByidAll` | 无 LIMIT 全表加载 |
| 22 | `MyWebSocketHandler` | 广播循环同步 `sendMessage`，慢客户端阻塞全体 |

---

## 三、可优化项

1. **参数校验**：引入 `spring-boot-starter-validation`，`@Valid` 校验请求体（用户名、密码、分页参数等）。
2. **DTO/VO 分离**：不直接返回 POJO（尤其 Users 带密码），统一出参模型。
3. **事务下沉**：`@Transactional` 应放在 Service 层而非 Controller；`UsersController.insert` 的多步写入（user+profile+artist）需包在一个事务里，失败回滚。
4. **分页统一**：用 MyBatis-Plus 分页插件替代手写 `offset`。
5. **单例化外部客户端**：`COSClient`、`RestTemplate` 注册为单例 Bean 复用。
6. **配置外置化**：`@ConfigurationProperties` + 环境变量，删除仓库中的密钥。
7. **清理死代码**：删除注释掉的缓存逻辑、无用字段/方法、重复依赖，修正 `untill`/`Untill` 命名。
8. **可观测性**：加 `spring-boot-starter-actuator` + 健康检查；关闭生产环境的 `StdOutImpl` SQL 日志（当前每句 SQL 都打 stdout）。
9. **统一代码风格**：生成器模板与手写代码混用，建议统一模板后重新生成。

---

## 四、大流量并发处理方案

### 现状盘点
- Tomcat 默认线程池（max 200）；HikariCP 默认连接池（10 连接）；
- 有 Redis 缓存但**失效策略错误**；有 Redisson 依赖但配置缺失；
- 点赞/浏览用 SQL 原子自增（`total_likes = total_likes + N`）——这一点本身是好的，可保留；
- 无任何限流、无异步化、无读写分离。

### 分层方案（按优先级）

**1. 缓存分层（解决读热点）**
- Redis 做 L1 缓存：Cache-Aside 模式，**写操作后主动删除相关缓存键**（或 `@Cacheable`/`@CacheEvict` 统一管理），避免当前"写后不失效"的脏数据；
- 本地 Caffeine 做 L2 缓存：热点文章详情、首页列表（秒级 TTL）；
- 缓存 key 规范化：`article:list:page:{page}:size:{size}`，避免拼接冲突。

**2. 计数类高并发（点赞/浏览/收藏）**
- 点赞/收藏记录表加**唯一索引** `(user_id, target_id, type)` 兜底防重复，插入前查 + 唯一约束双保险；
- 计数采用 Redis `INCR` 缓冲，定时/批量异步回写 MySQL；或继续用 SQL 原子自增（已满足）；
- 浏览量与"最近浏览"用 Redis + 延迟双删保证最终一致。

**3. 查询性能**
- `ORDER BY RAND()` 改为：预生成 id 列表缓存 Redis 随机取，或 `WHERE id >= FLOOR(RAND()*(SELECT MAX(id))) LIMIT n`；
- 搜索改用 MySQL 全文索引或引入 ES；列表查询强制 LIMIT；
- 补齐索引：`article(author_id)`、`article(article_block_id)`、`article(creative_time)`、`comments(article_id)`、点赞/关注联合索引。

**4. 限流与防刷**
- 登录接口（防爆破）、上传接口（防 DoS）用 Redisson `RRateLimiter` 或 Bucket4j 按 IP/用户限流；
- 网关层 Nginx 限流 + WAF。

**5. 异步化**
- 浏览计数、埋点、通知走 MQ（RocketMQ/Kafka）或 Redis Stream 异步落库；
- 文件上传改为**前端直传 COS（STS 临时密钥）**，后端只签发凭证，不占用 Tomcat 线程传大文件。

**6. WebSocket 扩展**
- 多实例部署时用 Redis Pub/Sub 或 MQ 做跨实例广播；
- 每个会话独立异步发送队列，超时剔除死连接，避免慢客户端阻塞广播。

**7. 网关与部署**
- Nginx 前置：静态资源、gzip、HTTP/2、HTTPS、负载均衡、限流；
- CDN 缓存文章图片/富文本静态资源；
- 当前数据库已是 **TiDB Cloud**（可水平扩展），配合读写分离 + 慢 SQL 监控；
- JVM 参数调优 + k8s HPA 按 CPU/QPS 水平伸缩 + 优雅停机。

**8. 可观测性**
- actuator + Prometheus/Grafana：QPS、RT、缓存命中率、连接池水位监控；
- 全链路日志（traceId），生产环境关闭 SQL stdout 日志。

**9. 压测**
- JMeter 对 `/article` 列表、详情、点赞、WebSocket 做压测，确认瓶颈（大概率在 ORDER BY RAND、无分页查询、缓存不失效导致的 DB 打满）。

---

## 五、修复优先级建议

| 优先级 | 事项 |
| --- | --- |
| P0（立即） | ①登录校验优先级/空指针 ②密码哈希脱敏（@JsonIgnore）③密钥外置并轮换 ④删硬编码 JWT 后门 ⑤日志脱敏 |
| P1（尽快） | ⑥缓存失效修复 ⑦AudioUpload 硬编码路径/路径穿越 ⑧COS 上传扩展名校验/防覆盖/连接复用 ⑨Redisson 配置缺失 ⑩Dockerfile jar 名 |
| P2（规划） | 分页/索引/随机文章优化、限流、异步化、WebSocket 改造、可观测性、压测 |
