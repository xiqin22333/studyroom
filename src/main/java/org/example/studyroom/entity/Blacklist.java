package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 黑名单表
 */
@Data
@TableName("blacklist")
public class Blacklist {

    /** 黑名单ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 原因 */
    private String reason;

    /** 开始时间 */
    @TableField("start_time")
    private LocalDateTime startTime;

    /** 结束时间 */
    @TableField("end_time")
    private LocalDateTime endTime;

    /** 1生效 0失效 */
    private Integer status;

    /** 操作人 */
    @TableField("operator_id")
    private Long operatorId;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
