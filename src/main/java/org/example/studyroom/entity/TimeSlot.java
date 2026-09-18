package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 * 预约时段表
 */
@Data
@TableName("time_slot")
public class TimeSlot {

    /** 时段ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 自习室ID，空表示全局 */
    @TableField("room_id")
    private Long roomId;

    /** 时段名称 */
    @TableField("slot_name")
    private String slotName;

    /** 开始时间 */
    @TableField("start_time")
    private LocalTime startTime;

    /** 结束时间 */
    @TableField("end_time")
    private LocalTime endTime;

    /** 1-7，空表示每天 */
    @TableField("day_of_week")
    private Integer dayOfWeek;

    /** 最大预约人数 */
    @TableField("max_reserve")
    private Integer maxReserve;

    /** 1启用 0停用 */
    private Integer status;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
