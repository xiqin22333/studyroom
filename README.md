# 校园自习室预约管理系统（前后端分离版）

## 架构

```
浏览器
  │
  ▼
前端  http://localhost:3000        (frontend/ 独立静态服务)
  │  ── /api/*、/upload/* 反向代理 ──►
  ▼
后端  http://localhost:8080        (Spring Boot，仅提供 REST API)
  │
  ▼
MySQL  (库名 csr)
```

- 前端：Vue 3 + Element Plus，位于 `frontend/`（index.html + lib/ 本地资源），由 `serve.py` 独立托管，不依赖后端。
- 后端：Spring Boot 3.5 + MyBatis-Plus，只输出 `/api/**` JSON 接口，已禁用默认静态资源映射（`spring.web.resources.add-mappings: false`），不再托管任何前端页面。
- 开发时前端通过 `serve.py` 把 `/api`、`/upload` 请求代理到后端，无需处理跨域；后端亦已配置 CORS，可直接用其他方式访问。

## 启动

### 方式一：一键启动

```bash
./start.sh
```

后端(8080) + 前端(3000) 同时启动，访问 http://localhost:3000

### 方式二：分别启动

```bash
# 终端 1：后端
bash mvnw spring-boot:run

# 终端 2：前端
cd frontend && python3 serve.py 3000
```

## 停止

```bash
lsof -ti:8080 | xargs kill -9
lsof -ti:3000 | xargs kill -9
```

## 目录结构

```
studyroom/
├── frontend/          # 独立前端工程
│   ├── index.html     # 单文件 SPA（Vue3 + Element Plus）
│   ├── lib/           # 本地化 CDN 资源
│   └── serve.py       # 静态服务器 + API 反向代理
├── src/main/java/     # 后端业务代码
├── src/main/resources/
│   └── application.yml
└── start.sh           # 一键启动脚本
```
