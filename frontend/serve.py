#!/usr/bin/env python3
"""前端独立开发服务器
- 托管 frontend 目录下的静态资源（index.html、lib/）
- 将 /api 与 /upload 请求反向代理到后端 Spring Boot（默认 8080）
用法: python3 serve.py [端口] [后端地址]
"""
import http.server
import socketserver
import urllib.request
import urllib.error
import os
import sys
import threading

FRONTEND_DIR = os.path.dirname(os.path.abspath(__file__))
PORT = int(sys.argv[1]) if len(sys.argv) > 1 else 3000
BACKEND = sys.argv[2] if len(sys.argv) > 2 else "http://localhost:8080"

PROXY_PREFIXES = ("/api/", "/upload/")

class ProxyHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=FRONTEND_DIR, **kwargs)

    def log_message(self, fmt, *args):
        sys.stderr.write("[%s] %s\n" % (self.log_date_time_string(), fmt % args))

    def _proxy(self):
        """把请求转发给后端，返回响应"""
        url = BACKEND + self.path
        body = None
        if self.command in ("POST", "PUT", "DELETE"):
            length = int(self.headers.get("Content-Length") or 0)
            body = self.rfile.read(length) if length > 0 else None
        req = urllib.request.Request(url, data=body, method=self.command)
        # 透传关键请求头
        for h in ("Content-Type", "Authorization"):
            if self.headers.get(h):
                req.add_header(h, self.headers[h])
        try:
            with urllib.request.urlopen(req, timeout=60) as resp:
                data = resp.read()
                self.send_response(resp.status)
                for k, v in resp.getheaders():
                    if k.lower() in ("content-type", "content-length"):
                        self.send_header(k, v)
                self.send_header("Access-Control-Allow-Origin", "*")
                self.end_headers()
                self.wfile.write(data)
        except urllib.error.HTTPError as e:
            data = e.read()
            self.send_response(e.code)
            self.send_header("Content-Type", "application/json; charset=utf-8")
            self.send_header("Access-Control-Allow-Origin", "*")
            self.end_headers()
            self.wfile.write(data)
        except Exception as e:
            self.send_response(502)
            self.send_header("Content-Type", "application/json; charset=utf-8")
            self.end_headers()
            self.wfile.write(('{"code":502,"message":"代理后端失败: %s"}' % e).encode("utf-8"))

    def do_GET(self):
        if self.path.startswith(PROXY_PREFIXES):
            self._proxy()
        else:
            super().do_GET()

    def do_POST(self):
        if self.path.startswith(PROXY_PREFIXES):
            self._proxy()
        else:
            self.send_response(404); self.end_headers()

    def do_PUT(self):
        if self.path.startswith(PROXY_PREFIXES):
            self._proxy()
        else:
            self.send_response(404); self.end_headers()

    def do_DELETE(self):
        if self.path.startswith(PROXY_PREFIXES):
            self._proxy()
        else:
            self.send_response(404); self.end_headers()

    def do_OPTIONS(self):
        self.send_response(204)
        self.send_header("Access-Control-Allow-Origin", "*")
        self.send_header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
        self.send_header("Access-Control-Allow-Headers", "*")
        self.end_headers()

class ThreadingHTTPServer(socketserver.ThreadingMixIn, http.server.HTTPServer):
    daemon_threads = True

if __name__ == "__main__":
    os.chdir(FRONTEND_DIR)
    server = ThreadingHTTPServer(("0.0.0.0", PORT), ProxyHandler)
    print("前端服务已启动: http://localhost:%d  (代理后端 %s)" % (PORT, BACKEND))
    print("静态目录: %s" % FRONTEND_DIR)
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        server.shutdown()
