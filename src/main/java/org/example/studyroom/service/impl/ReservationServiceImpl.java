package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.example.studyroom.common.BusinessException;
import org.example.studyroom.entity.CheckinRecord;
import org.example.studyroom.entity.Reservation;
import org.example.studyroom.entity.ReservationRule;
import org.example.studyroom.entity.Seat;
import org.example.studyroom.entity.SysUser;
import org.example.studyroom.entity.TimeSlot;
import org.example.studyroom.mapper.ReservationMapper;
import org.example.studyroom.service.CheckinRecordService;
import org.example.studyroom.service.CreditLogService;
import org.example.studyroom.service.ReservationRuleService;
import org.example.studyroom.service.ReservationService;
import org.example.studyroom.service.SeatService;
import org.example.studyroom.service.SysUserService;
import org.example.studyroom.service.TimeSlotService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预约记录表 Service 实现
 * 核心流程：预约（校验规则+查冲突）→ 签到 → 签退；支持取消
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements ReservationService {

    private final SysUserService sysUserService;
    private final SeatService seatService;
    private final TimeSlotService timeSlotService;
    private final ReservationRuleService reservationRuleService;
    private final CheckinRecordService checkinRecordService;
    private final CreditLogService creditLogService;

    /** 状态：0待签到 1已签到 2已完成 3已取消 4爽约 5违规取消 */
    private static final int STATUS_WAIT = 0;
    private static final int STATUS_CHECKED_IN = 1;
    private static final int STATUS_DONE = 2;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Reservation createReservation(Reservation request) {
        // 1. 基本参数检查
        if (request.getUserId() == null || request.getSeatId() == null || request.getReserveDate() == null) {
            throw new BusinessException("预约用户、座位、日期不能为空");
        }
        // 2. 用户校验
        SysUser user = sysUserService.getById(request.getUserId());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BusinessException("账号不可用，无法预约");
        }
        // 3. 座位校验
        Seat seat = seatService.getById(request.getSeatId());
        if (seat == null) {
            throw new BusinessException("座位不存在");
        }
        if (seat.getStatus() == null || seat.getStatus() != 1) {
            throw new BusinessException("座位当前不可用");
        }
        // 4. 确定起止时间：优先从时段表取
        LocalTime startTime = request.getStartTime();
        LocalTime endTime = request.getEndTime();
        if (request.getSlotId() != null) {
            TimeSlot slot = timeSlotService.getById(request.getSlotId());
            if (slot == null || slot.getStatus() == null || slot.getStatus() != 1) {
                throw new BusinessException("所选时段不存在或已停用");
            }
            startTime = slot.getStartTime();
            endTime = slot.getEndTime();
            request.setStartTime(startTime);
            request.setEndTime(endTime);
        }
        if (startTime == null || endTime == null) {
            throw new BusinessException("请选择预约时段或填写起止时间");
        }
        if (!endTime.isAfter(startTime)) {
            throw new BusinessException("结束时间必须晚于开始时间");
        }
        // 5. 预约规则校验（读取启用状态的第一条规则）
        ReservationRule rule = reservationRuleService.lambdaQuery()
                .eq(ReservationRule::getStatus, 1)
                .orderByAsc(ReservationRule::getId)
                .last("limit 1").one();
        if (rule != null) {
            checkRule(rule, user, request, startTime, endTime);
        }
        // 6. 冲突检测：同一座位同一天、时间有重叠的生效预约
        long conflictCount = lambdaQuery()
                .eq(Reservation::getSeatId, request.getSeatId())
                .eq(Reservation::getReserveDate, request.getReserveDate())
                .in(Reservation::getStatus, STATUS_WAIT, STATUS_CHECKED_IN, STATUS_DONE)
                .le(Reservation::getStartTime, endTime)
                .ge(Reservation::getEndTime, startTime)
                .count();
        if (conflictCount > 0) {
            throw new BusinessException("该座位在所选时段已被预约，请选择其它座位或时段");
        }
        // 7. 组装并保存
        request.setReservationNo(generateReservationNo());
        request.setCheckinCode(generateCheckinCode());
        request.setStatus(STATUS_WAIT);
        save(request);
        return request;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelReservation(Long id, String reason) {
        Reservation reservation = getById(id);
        if (reservation == null) {
            throw new BusinessException("预约不存在");
        }
        if (reservation.getStatus() != STATUS_WAIT) {
            throw new BusinessException("只有待签到的预约才能取消");
        }
        // 判断是否临近开始：距开始不足30分钟算违规取消，扣2分
        boolean lateCancel = false;
        if (reservation.getStartTime() != null) {
            LocalDateTime start = LocalDateTime.of(reservation.getReserveDate(), reservation.getStartTime());
            long minutes = Duration.between(LocalDateTime.now(), start).toMinutes();
            if (minutes < 30) {
                lateCancel = true;
            }
        }
        Reservation update = new Reservation();
        update.setId(id);
        update.setStatus(lateCancel ? 5 : 3);
        update.setCancelReason(reason);
        update.setCancelledAt(LocalDateTime.now());
        updateById(update);
        if (lateCancel) {
            creditLogService.changeScore(reservation.getUserId(), -2, "临近开始取消（<30分钟）-2", "reservation", id);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long checkin(Long reservationId, Integer checkinType, Long deviceId) {
        Reservation reservation = getById(reservationId);
        if (reservation == null) {
            throw new BusinessException("预约不存在");
        }
        if (reservation.getStatus() != STATUS_WAIT) {
            throw new BusinessException("当前状态不可签到（仅待签到的预约可签到）");
        }
        // 写签到记录
        CheckinRecord record = new CheckinRecord();
        record.setReservationId(reservationId);
        record.setUserId(reservation.getUserId());
        record.setCheckinTime(LocalDateTime.now());
        record.setCheckinType(checkinType == null ? 3 : checkinType);
        record.setDeviceId(deviceId);
        record.setCheckinStatus(1);
        checkinRecordService.save(record);
        // 预约状态流转：待签到 → 已签到
        Reservation update = new Reservation();
        update.setId(reservationId);
        update.setStatus(STATUS_CHECKED_IN);
        updateById(update);
        // 信用分：签到成功 +1
        creditLogService.changeScore(reservation.getUserId(), 1, "签到成功 +1", "reservation", reservationId);
        return record.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean checkout(Long reservationId) {
        Reservation reservation = getById(reservationId);
        if (reservation == null) {
            throw new BusinessException("预约不存在");
        }
        if (reservation.getStatus() != STATUS_CHECKED_IN) {
            throw new BusinessException("当前状态不可签退（仅已签到的预约可签退）");
        }
        // 更新签到记录的签退时间
        CheckinRecord record = checkinRecordService.lambdaQuery()
                .eq(CheckinRecord::getReservationId, reservationId)
                .orderByDesc(CheckinRecord::getId)
                .last("limit 1").one();
        if (record != null) {
            CheckinRecord update = new CheckinRecord();
            update.setId(record.getId());
            update.setCheckoutTime(LocalDateTime.now());
            checkinRecordService.updateById(update);
        }
        // 预约状态流转：已签到 → 已完成
        Reservation update = new Reservation();
        update.setId(reservationId);
        update.setStatus(STATUS_DONE);
        updateById(update);
        // 信用分：正常完成 +1
        creditLogService.changeScore(reservation.getUserId(), 1, "正常签退 +1", "reservation", reservationId);
        return true;
    }

    @Override
    public List<Reservation> myReservations(Long userId, Integer status) {
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Reservation::getUserId, userId);
        if (status != null) {
            wrapper.eq(Reservation::getStatus, status);
        }
        wrapper.orderByDesc(Reservation::getId);
        return list(wrapper);
    }

    @Override
    public List<Reservation> findOccupied(Long roomId, Long seatId, LocalDate date, Long slotId) {
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
        if (roomId != null) {
            wrapper.eq(Reservation::getRoomId, roomId);
        }
        if (seatId != null) {
            wrapper.eq(Reservation::getSeatId, seatId);
        }
        if (date != null) {
            wrapper.eq(Reservation::getReserveDate, date);
        }
        if (slotId != null) {
            wrapper.eq(Reservation::getSlotId, slotId);
        }
        wrapper.in(Reservation::getStatus, STATUS_WAIT, STATUS_CHECKED_IN, STATUS_DONE);
        wrapper.orderByAsc(Reservation::getReserveDate).orderByAsc(Reservation::getStartTime);
        return list(wrapper);
    }

    /** 规则校验 */
    private void checkRule(ReservationRule rule, SysUser user, Reservation request,
                           LocalTime startTime, LocalTime endTime) {
        // 信用分阈值
        if (rule.getCreditThreshold() != null && rule.getCreditThreshold() > 0
                && user.getCreditScore() != null && user.getCreditScore() < rule.getCreditThreshold()) {
            throw new BusinessException("信用分不足，无法预约（需不低于 " + rule.getCreditThreshold() + " 分）");
        }
        // 日期范围：不能约过去，不能超过提前天数
        LocalDate today = LocalDate.now();
        if (request.getReserveDate().isBefore(today)) {
            throw new BusinessException("不能预约过去的日期");
        }
        if (rule.getAdvanceReserveDays() != null && rule.getAdvanceReserveDays() > 0
                && request.getReserveDate().isAfter(today.plusDays(rule.getAdvanceReserveDays()))) {
            throw new BusinessException("最多只能提前 " + rule.getAdvanceReserveDays() + " 天预约");
        }
        // 每日最大预约次数
        if (rule.getMaxReservePerDay() != null && rule.getMaxReservePerDay() > 0) {
            long count = lambdaQuery()
                    .eq(Reservation::getUserId, request.getUserId())
                    .eq(Reservation::getReserveDate, request.getReserveDate())
                    .in(Reservation::getStatus, STATUS_WAIT, STATUS_CHECKED_IN, STATUS_DONE)
                    .count();
            if (count >= rule.getMaxReservePerDay()) {
                throw new BusinessException("当天预约次数已达上限（" + rule.getMaxReservePerDay() + " 次）");
            }
        }
        // 单次最长时长
        long minutes = Duration.between(startTime, endTime).toMinutes();
        if (rule.getMaxReserveDuration() != null && rule.getMaxReserveDuration() > 0
                && minutes > rule.getMaxReserveDuration()) {
            throw new BusinessException("单次预约时长不能超过 " + rule.getMaxReserveDuration() + " 分钟");
        }
    }

    /** 预约编号：RS + 年月日时分秒 + 4位随机数 */
    private String generateReservationNo() {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "RS" + time + String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    /** 签到码：CK + 8位随机数字 */
    private String generateCheckinCode() {
        return "CK" + String.format("%08d", ThreadLocalRandom.current().nextInt(100000000));
    }
}
