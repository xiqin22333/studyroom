package org.example.studyroom.controller.reservation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.TimeSlot;
import org.example.studyroom.service.TimeSlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 预约时段表 控制器（预约管理-时段配置）
 */
@RestController
@RequestMapping("/api/reservation/time-slot")
@RequiredArgsConstructor
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<TimeSlot>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<TimeSlot> page = timeSlotService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<TimeSlot> getById(@PathVariable Long id) {
        return Result.success(timeSlotService.getById(id));
    }

    /** 新增 */
    @PostMapping
    @OperLog(module = "预约时段", action = "新增时段")
    public Result<Boolean> add(@RequestBody TimeSlot entity) {
        return Result.success(timeSlotService.save(entity));
    }

    /** 修改 */
    @PutMapping
    @OperLog(module = "预约时段", action = "修改时段")
    public Result<Boolean> update(@RequestBody TimeSlot entity) {
        return Result.success(timeSlotService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    @OperLog(module = "预约时段", action = "删除时段")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(timeSlotService.removeById(id));
    }
}
