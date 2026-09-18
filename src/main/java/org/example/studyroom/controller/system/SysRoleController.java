package org.example.studyroom.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.SysRole;
import org.example.studyroom.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 角色表 控制器（系统管理-角色管理）
 */
@RestController
@RequestMapping("/api/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<SysRole>> page(@RequestParam(defaultValue = "1") long current,
                                            @RequestParam(defaultValue = "10") long size) {
        Page<SysRole> page = sysRoleService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 查询全部启用角色（给用户分配角色时下拉用） */
    @GetMapping("/list")
    public Result<List<SysRole>> list() {
        return Result.success(sysRoleService.lambdaQuery().eq(SysRole::getStatus, 1).list());
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<SysRole> getById(@PathVariable Long id) {
        return Result.success(sysRoleService.getById(id));
    }

    /** 新增 */
    @PostMapping
    @OperLog(module = "角色管理", action = "新增角色")
    public Result<Boolean> add(@RequestBody SysRole entity) {
        return Result.success(sysRoleService.save(entity));
    }

    /** 修改 */
    @PutMapping
    @OperLog(module = "角色管理", action = "修改角色")
    public Result<Boolean> update(@RequestBody SysRole entity) {
        return Result.success(sysRoleService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    @OperLog(module = "角色管理", action = "删除角色")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysRoleService.removeById(id));
    }
}
