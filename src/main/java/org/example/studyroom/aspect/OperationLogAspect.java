package org.example.studyroom.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.entity.OperationLog;
import org.example.studyroom.service.OperationLogService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作日志切面：拦截所有标注了 @OperLog 的方法，自动记录操作日志到 operation_log 表。
 * 记录内容：操作人（从请求头 userId 读取）、模块、动作、请求方法、URL、IP、参数、返回结果。
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final OperationLogService operationLogService;
    private final ObjectMapper objectMapper;

    @Around("@annotation(operLog)")
    public Object record(ProceedingJoinPoint joinPoint, OperLog operLog) throws Throwable {
        long start = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            // 业务异常由全局异常处理器统一返回，这里不再重复处理，但异常结果不记录
            throw e;
        }
        try {
            saveLog(joinPoint, operLog, result);
        } catch (Exception ex) {
            // 记录日志失败不能影响业务，只打印警告
            log.warn("记录操作日志失败: {}", ex.getMessage());
        }
        long cost = System.currentTimeMillis() - start;
        log.info("操作日志: {} - {} 耗时 {}ms", operLog.module(), operLog.action(), cost);
        return result;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, OperLog operLog, Object result) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        OperationLog log = new OperationLog();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.setMethod(request.getMethod());
            log.setUrl(request.getRequestURI());
            log.setIp(getClientIp(request));
            log.setUserAgent(truncate(request.getHeader("User-Agent"), 255));

            String userIdHeader = request.getHeader("userId");
            if (userIdHeader != null && !userIdHeader.isBlank()) {
                try {
                    log.setUserId(Long.valueOf(userIdHeader.trim()));
                } catch (NumberFormatException ignored) {
                    log.setUserId(null);
                }
            }
        }
        log.setModule(operLog.module());
        log.setAction(operLog.action());
        log.setParams(toJson(joinPoint.getArgs()));
        log.setResult(toJson(result));
        operationLogService.save(log);
    }

    /** 简单取客户端 IP：优先取反向代理转发头 */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim();
        }
        return truncate(ip, 50);
    }

    private String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return truncate(objectMapper.writeValueAsString(obj), 2000);
        } catch (Exception e) {
            return "参数序列化失败";
        }
    }

    private String truncate(String text, int maxLength) {
        if (text == null) {
            return null;
        }
        return text.length() > maxLength ? text.substring(0, maxLength) : text;
    }
}
