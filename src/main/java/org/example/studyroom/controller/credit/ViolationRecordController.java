package org.example.studyroom.controller.credit;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.ViolationRecord;
import org.example.studyroom.service.ViolationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 违规记录表 控制器
 */
@RestController
@RequestMapping("/api/credit/violation")
@RequiredArgsConstructor
public class ViolationRecordController {

    private final ViolationRecordService violationRecordService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<ViolationRecord>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<ViolationRecord> page = violationRecordService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<ViolationRecord> getById(@PathVariable Long id) {
        return Result.success(violationRecordService.getById(id));
    }

    /** 新增 */
    @PostMapping
    public Result<Boolean> add(@RequestBody ViolationRecord entity) {
        return Result.success(violationRecordService.save(entity));
    }

    /** 修改 */
    @PutMapping
    public Result<Boolean> update(@RequestBody ViolationRecord entity) {
        return Result.success(violationRecordService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(violationRecordService.removeById(id));
    }
}
