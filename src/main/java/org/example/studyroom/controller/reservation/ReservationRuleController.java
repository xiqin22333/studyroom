package org.example.studyroom.controller.reservation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.ReservationRule;
import org.example.studyroom.service.ReservationRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 预约规则表 控制器（预约管理-规则配置）
 */
@RestController
@RequestMapping("/api/reservation/rule")
@RequiredArgsConstructor
public class ReservationRuleController {

    private final ReservationRuleService reservationRuleService;

    /** 分页查询 */
    @GetMapping("/page")
    public Result<PageResult<ReservationRule>> page(@RequestParam(defaultValue = "1") long current,
                                         @RequestParam(defaultValue = "10") long size) {
        Page<ReservationRule> page = reservationRuleService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<ReservationRule> getById(@PathVariable Long id) {
        return Result.success(reservationRuleService.getById(id));
    }

    /** 新增 */
    @PostMapping
    @OperLog(module = "预约规则", action = "新增规则")
    public Result<Boolean> add(@RequestBody ReservationRule entity) {
        return Result.success(reservationRuleService.save(entity));
    }

    /** 修改 */
    @PutMapping
    @OperLog(module = "预约规则", action = "修改规则")
    public Result<Boolean> update(@RequestBody ReservationRule entity) {
        return Result.success(reservationRuleService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    @OperLog(module = "预约规则", action = "删除规则")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(reservationRuleService.removeById(id));
    }
}
