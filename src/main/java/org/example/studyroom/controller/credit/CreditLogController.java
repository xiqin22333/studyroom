package org.example.studyroom.controller.credit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.CreditLog;
import org.example.studyroom.service.CreditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 信用分流水表 控制器
 */
@RestController
@RequestMapping("/api/credit/log")
@RequiredArgsConstructor
public class CreditLogController {

    private final CreditLogService creditLogService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<CreditLog>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<CreditLog> page = creditLogService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<CreditLog> getById(@PathVariable Long id) {
        return Result.success(creditLogService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody CreditLog entity) {
        return Result.success(creditLogService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody CreditLog entity) {
        return Result.success(creditLogService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(creditLogService.removeById(id));
    }
}
