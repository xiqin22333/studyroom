#!/bin/bash

cd "$(dirname "$0")"

echo "== 启动后端 (8080) =="
nohup bash mvnw spring-boot:run > /tmp/studyroom-backend.log 2>&1 &
BACKEND_PID=$!
echo "后端 PID: $BACKEND_PID (日志: /tmp/studyroom-backend.log)"

sleep 12

echo "== 启动前端 (3000) =="
nohup python3 frontend/serve.py 3000 > /tmp/studyroom-frontend.log 2>&1 &
FRONTEND_PID=$!
echo "前端 PID: $FRONTEND_PID (日志: /tmp/studyroom-frontend.log)"

echo ""
echo "访问 http://localhost:3000 使用系统"
echo "停止: kill $BACKEND_PID $FRONTEND_PID"
