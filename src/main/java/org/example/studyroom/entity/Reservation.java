package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 * 预约记录表
 */
@Data
@TableName("reservation")
public class Reservation {

    /** 预约ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 预约编号 */
    @TableField("reservation_no")
    private String reservationNo;

    /** 预约用户 */
    @TableField("user_id")
    private Long userId;

    /** 自习室 */
    @TableField("room_id")
    private Long roomId;

    /** 座位 */
    @TableField("seat_id")
    private Long seatId;

    /** 时段 */
    @TableField("slot_id")
    private Long slotId;

    /** 预约日期 */
    @TableField("reserve_date")
    private LocalDate reserveDate;

    /** 开始时间 */
    @TableField("start_time")
    private LocalTime startTime;

    /** 结束时间 */
    @TableField("end_time")
    private LocalTime endTime;

    /** 0待签到 1已签到 2已完成 3已取消 4爽约 5违规取消 */
    private Integer status;

    /** 签到码 */
    @TableField("checkin_code")
    private String checkinCode;

    /** 二维码内容 */
    @TableField("qr_code")
    private String qrCode;

    /** 取消原因 */
    @TableField("cancel_reason")
    private String cancelReason;

    /** 取消时间 */
    @TableField("cancelled_at")
    private LocalDateTime cancelledAt;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
