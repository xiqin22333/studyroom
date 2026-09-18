package org.example.studyroom.controller.reservation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.CheckinRecord;
import org.example.studyroom.service.CheckinRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 签到记录表 控制器（预约管理-签到记录，正常签到走 /api/reservation/reservation/checkin）
 */
@RestController
@RequestMapping("/api/reservation/checkin")
@RequiredArgsConstructor
public class CheckinRecordController {

    private final CheckinRecordService checkinRecordService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<CheckinRecord>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<CheckinRecord> page = checkinRecordService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<CheckinRecord> getById(@PathVariable Long id) {
        return Result.success(checkinRecordService.getById(id));
    }

    /** 新增 */
    @PostMapping
    @OperLog(module = "签到记录", action = "新增签到记录")
    public Result<Boolean> add(@RequestBody CheckinRecord entity) {
        return Result.success(checkinRecordService.save(entity));
    }

    /** 修改 */
    @PutMapping
    @OperLog(module = "签到记录", action = "修改签到记录")
    public Result<Boolean> update(@RequestBody CheckinRecord entity) {
        return Result.success(checkinRecordService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    @OperLog(module = "签到记录", action = "删除签到记录")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(checkinRecordService.removeById(id));
    }
}
