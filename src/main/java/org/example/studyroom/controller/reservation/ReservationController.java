package org.example.studyroom.controller.reservation;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.time.LocalDate;
import java.util.List;
import org.example.studyroom.annotation.OperLog;
import org.example.studyroom.common.PageResult;
import org.example.studyroom.common.Result;
import org.example.studyroom.entity.Reservation;
import org.example.studyroom.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

/**
 * 预约记录表 控制器（预约管理）
 * 路径统一以 /api/reservation/reservation 开头
 */
@RestController
@RequestMapping("/api/reservation/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    /** 分页查询（管理员看全部预约） */
    @GetMapping("/page")
    public Result<PageResult<Reservation>> page(@RequestParam(defaultValue = "1") long current,
                                                @RequestParam(defaultValue = "10") long size) {
        Page<Reservation> page = reservationService.page(new Page<>(current, size));
        return Result.success(new PageResult<>(page));
    }

    /** 根据ID查询 */
    @GetMapping("/{id}")
    public Result<Reservation> getById(@PathVariable Long id) {
        return Result.success(reservationService.getById(id));
    }

    /** 我的预约列表（status 可选：0待签到 1已签到 2已完成 3已取消） */
    @GetMapping("/my")
    public Result<List<Reservation>> my(@RequestParam Long userId,
                                        @RequestParam(required = false) Integer status) {
        return Result.success(reservationService.myReservations(userId, status));
    }

    /** 占用查询：查某座位/自习室在某日期/时段被谁占了 */
    @GetMapping("/occupied")
    public Result<List<Reservation>> occupied(@RequestParam(required = false) Long roomId,
                                              @RequestParam(required = false) Long seatId,
                                              @RequestParam(required = false)
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                              @RequestParam(required = false) Long slotId) {
        return Result.success(reservationService.findOccupied(roomId, seatId, date, slotId));
    }

    /** 发起预约：自动校验用户/座位/规则/冲突，生成预约编号，状态=0待签到 */
    @PostMapping
    @OperLog(module = "预约管理", action = "发起预约")
    public Result<Reservation> create(@RequestBody Reservation entity) {
        return Result.success(reservationService.createReservation(entity));
    }

    /** 取消预约（仅待签到状态可取消） */
    @PostMapping("/cancel/{id}")
    @OperLog(module = "预约管理", action = "取消预约")
    public Result<Boolean> cancel(@PathVariable Long id,
                                  @RequestParam(required = false) String reason) {
        return Result.success(reservationService.cancelReservation(id, reason));
    }

    /** 签到（预约 0待签到 → 1已签到） */
    @PostMapping("/checkin")
    @OperLog(module = "预约管理", action = "签到")
    public Result<Long> checkin(@RequestParam Long reservationId,
                                @RequestParam(required = false) Integer checkinType,
                                @RequestParam(required = false) Long deviceId) {
        return Result.success(reservationService.checkin(reservationId, checkinType, deviceId));
    }

    /** 签退（预约 1已签到 → 2已完成） */
    @PostMapping("/checkout")
    @OperLog(module = "预约管理", action = "签退")
    public Result<Boolean> checkout(@RequestParam Long reservationId) {
        return Result.success(reservationService.checkout(reservationId));
    }

    /** 修改（管理员修正异常数据用） */
    @PutMapping
    @OperLog(module = "预约管理", action = "修改预约")
    public Result<Boolean> update(@RequestBody Reservation entity) {
        return Result.success(reservationService.updateById(entity));
    }

    /** 删除 */
    @DeleteMapping("/{id}")
    @OperLog(module = "预约管理", action = "删除预约")
    public Result<Boolean> delete(@PathVariable Long id) {
        return Result.success(reservationService.removeById(id));
    }
}
