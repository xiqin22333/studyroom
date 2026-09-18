package org.example.studyroom.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.SysUserRole;
import org.example.studyroom.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户角色关联表 控制器
 */
@RestController
@RequestMapping("/api/system/user-role")
@RequiredArgsConstructor
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<SysUserRole>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<SysUserRole> page = sysUserRoleService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<SysUserRole> getById(@PathVariable Long id) {
        return Result.success(sysUserRoleService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody SysUserRole entity) {
        return Result.success(sysUserRoleService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody SysUserRole entity) {
        return Result.success(sysUserRoleService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysUserRoleService.removeById(id));
    }
}
