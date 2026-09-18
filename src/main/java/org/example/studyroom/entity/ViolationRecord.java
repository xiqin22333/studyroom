package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 违规记录表
 */
@Data
@TableName("violation_record")
public class ViolationRecord {

    /** 违规ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 关联预约 */
    @TableField("reservation_id")
    private Long reservationId;

    /** 1爽约 2超时 3代签 4占座 5其他 */
    @TableField("violation_type")
    private Integer violationType;

    /** 违规描述 */
    private String description;

    /** 扣除信用分 */
    @TableField("deduct_score")
    private Integer deductScore;

    /** 1警告 2扣分 3禁用 4黑名单 */
    @TableField("punish_type")
    private Integer punishType;

    /** 0待处理 1已处理 */
    private Integer status;

    /** 处理人 */
    @TableField("handler_id")
    private Long handlerId;

    /** 处理时间 */
    @TableField("handled_at")
    private LocalDateTime handledAt;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
