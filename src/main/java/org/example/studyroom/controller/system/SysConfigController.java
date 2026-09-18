package org.example.studyroom.controller.system;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.SysConfig;
import org.example.studyroom.service.SysConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统配置表 控制器（系统管理-参数配置）
 */
@RestController
@RequestMapping("/api/system/config")
@RequiredArgsConstructor
public class SysConfigController {

    private final SysConfigService sysConfigService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<SysConfig>> page(@RequestParam(defaultValue = "1") long current,
                                              @RequestParam(defaultValue = "10") long size) {
        Page<SysConfig> page = sysConfigService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 按配置键查询（如 /key/credit.init） */
    @GetMapping("/key/{configKey}")
    public Result<SysConfig> getByKey(@PathVariable String configKey) {
        SysConfig config = sysConfigService.lambdaQuery()
                .eq(SysConfig::getConfigKey, configKey).one();
        return Result.success(config);
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<SysConfig> getById(@PathVariable Long id) {
        return Result.success(sysConfigService.getById(id));
    }

    /** 新增 */
    @PostMapping
    @OperLog(module = "参数配置", action = "新增配置")
    public Result<Boolean> add(@RequestBody SysConfig entity) {
        return Result.success(sysConfigService.save(entity));
    }

    /** 修改 */
    @PutMapping
    @OperLog(module = "参数配置", action = "修改配置")
    public Result<Boolean> update(@RequestBody SysConfig entity) {
        return Result.success(sysConfigService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    @OperLog(module = "参数配置", action = "删除配置")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(sysConfigService.removeById(id));
    }
}
