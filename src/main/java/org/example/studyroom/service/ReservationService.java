package org.example.studyroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.time.LocalDate;
import java.util.List;
import org.example.studyroom.entity.Reservation;

/**
 * 预约记录表 Service
 */
public interface ReservationService extends IService<Reservation> {

    /** 发起预约：校验用户/座位/规则/冲突，生成预约编号与签到码，状态置为 0 待签到 */
    Reservation createReservation(Reservation request);

    /** 取消预约：仅待签到状态可取消 */
    boolean cancelReservation(Long id, String reason);

    /** 签到：预约状态 0→1，并写入签到记录 */
    Long checkin(Long reservationId, Integer checkinType, Long deviceId);

    /** 签退：预约状态 1→2（已完成），记录签退时间 */
    boolean checkout(Long reservationId);

    /** 我的预约列表（可按状态筛选） */
    List<Reservation> myReservations(Long userId, Integer status);

    /** 占用查询：查某自习室/座位/日期/时段下已生效的预约（用于"哪些被占了"） */
    List<Reservation> findOccupied(Long roomId, Long seatId, LocalDate date, Long slotId);
}
