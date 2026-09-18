package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 签到记录表
 */
@Data
@TableName("checkin_record")
public class CheckinRecord {

    /** 签到ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 预约ID */
    @TableField("reservation_id")
    private Long reservationId;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 签到时间 */
    @TableField("checkin_time")
    private LocalDateTime checkinTime;

    /** 签退时间 */
    @TableField("checkout_time")
    private LocalDateTime checkoutTime;

    /** 1扫码 2人脸 3手动 */
    @TableField("checkin_type")
    private Integer checkinType;

    /** 设备ID */
    @TableField("device_id")
    private Long deviceId;

    /** 1成功 0失败 */
    @TableField("checkin_status")
    private Integer checkinStatus;

    /** 备注 */
    private String remark;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
