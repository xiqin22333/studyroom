package org.example.studyroom.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.SysUser;
import org.example.studyroom.service.SysUserService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 用户表 控制器（系统管理-用户管理）
 * 路径统一以 /api/system/user 开头
 */
@RestController
@RequestMapping("/api/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    /** 分页查询（支持按用户名/姓名模糊搜索、按 userType 过滤，结果带角色ID） */
    @GetMapping("/page")
    public Result<PageResult<SysUser>> page(@RequestParam(defaultValue = "1") long current,
                                            @RequestParam(defaultValue = "10") long size,
                                            @RequestParam(required = false) String keyword,
                                            @RequestParam(required = false) Integer userType) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(SysUser::getUsername, keyword)
                    .or().like(SysUser::getRealName, keyword);
        }
        if (userType != null) {
            wrapper.eq(SysUser::getUserType, userType);
        }
        wrapper.orderByDesc(SysUser::getId);
        Page<SysUser> page = sysUserService.page(new Page<>(current, size), wrapper);
        // 给每个用户补上角色ID列表
        for (SysUser user : page.getRecords()) {
            user.setRoleIds(sysUserService.getRoleIds(user.getId()));
        }
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询（带角色ID） */
    @GetMapping("/{id}")
    public Result<SysUser> getById(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user != null) {
            user.setRoleIds(sysUserService.getRoleIds(user.getId()));
        }
        return Result.success(user);
    }

    /** 新增用户（body 里可带 roleIds 数组分配角色；不传密码默认 123456） */
    @PostMapping
    @OperLog(module = "用户管理", action = "新增用户")
    public Result<Long> add(@RequestBody SysUser entity) {
        return Result.success(sysUserService.addUser(entity, entity.getRoleIds()));
    }

    /** 修改用户（传了密码才改密码；传了 roleIds 才重新分配角色） */
    @PutMapping
    @OperLog(module = "用户管理", action = "修改用户")
    public Result<Boolean> update(@RequestBody SysUser entity) {
        return Result.success(sysUserService.updateUser(entity, entity.getRoleIds()));
    }

    /** 删除用户（逻辑删除：实际是给 deleted_at 打上删除时间，数据还在数据库里） */
    @DeleteMapping("/{id}")
    @OperLog(module = "用户管理", action = "删除用户")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysUserService.removeById(id));
    }

    /** 重置密码（newPassword 不传默认重置为 123456） */
    @PostMapping("/reset-password")
    @OperLog(module = "用户管理", action = "重置密码")
    public Result<Boolean> resetPassword(@RequestParam Long id,
                                         @RequestParam(required = false) String newPassword) {
        return Result.success(sysUserService.resetPassword(id, newPassword));
    }

    /** 启用/禁用账号（status: 1正常 0禁用） */
    @PutMapping("/status")
    @OperLog(module = "用户管理", action = "启用/禁用账号")
    public Result<Boolean> changeStatus(@RequestParam Long id, @RequestParam Integer status) {
        return Result.success(sysUserService.changeStatus(id, status));
    }

    /** 查询用户拥有的角色ID（供前端分配角色时回显） */
    @GetMapping("/{id}/roles")
    public Result<List<Long>> getUserRoleIds(@PathVariable Long id) {
        return Result.success(sysUserService.getRoleIds(id));
    }
}
