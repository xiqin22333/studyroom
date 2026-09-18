package org.example.studyroom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志注解：标在 Controller 方法上，被 AOP 切面拦截后自动记录一条操作日志。
 * 例：@OperLog(module = "用户管理", action = "新增用户")
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OperLog {

    /** 所属模块，如：用户管理、角色管理 */
    String module() default "";

    /** 具体动作，如：新增用户、重置密码 */
    String action() default "";
}
