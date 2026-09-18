package org.example.studyroom.controller.system;

import lombok.RequiredArgsConstructor;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.Result;
import org.example.studyroom.dto.LoginRequest;
import org.example.studyroom.dto.LoginResponse;
import org.example.studyroom.entity.SysUser;
import org.example.studyroom.service.SysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录认证 控制器
 */
@RestController
@RequestMapping("/api/system/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SysUserService sysUserService;

    /** 登录：校验账号密码，返回用户信息 + 角色 */
    @PostMapping("/login")
    @OperLog(module = "系统管理", action = "登录")
    public Result<LoginResponse> login(@RequestBody LoginRequest request) {
        return Result.success(sysUserService.login(request));
    }

    /** 学生自助注册 */
    @PostMapping("/register")
    @OperLog(module = "系统管理", action = "学生注册")
    public Result<Long> register(@RequestBody SysUser user) {
        return Result.success(sysUserService.registerStudent(user));
    }
}
