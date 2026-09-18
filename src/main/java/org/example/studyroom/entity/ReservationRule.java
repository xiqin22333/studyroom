package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 预约规则表
 */
@Data
@TableName("reservation_rule")
public class ReservationRule {

    /** 规则ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 规则名称 */
    @TableField("rule_name")
    private String ruleName;

    /** 规则编码 */
    @TableField("rule_code")
    private String ruleCode;

    /** 每日最大预约次数 */
    @TableField("max_reserve_per_day")
    private Integer maxReservePerDay;

    /** 单次最长分钟数 */
    @TableField("max_reserve_duration")
    private Integer maxReserveDuration;

    /** 可提前预约天数 */
    @TableField("advance_reserve_days")
    private Integer advanceReserveDays;

    /** 提前取消分钟数 */
    @TableField("cancel_deadline_minutes")
    private Integer cancelDeadlineMinutes;

    /** 签到开始提前分钟 */
    @TableField("checkin_start_minutes")
    private Integer checkinStartMinutes;

    /** 签到截止延后分钟 */
    @TableField("checkin_end_minutes")
    private Integer checkinEndMinutes;

    /** 信用分阈值 */
    @TableField("credit_threshold")
    private Integer creditThreshold;

    /** 1启用 0停用 */
    private Integer status;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
