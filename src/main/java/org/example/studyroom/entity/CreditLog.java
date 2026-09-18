package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 信用分流水表
 */
@Data
@TableName("credit_log")
public class CreditLog {

    /** 流水ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 变动分数 */
    @TableField("change_score")
    private Integer changeScore;

    /** 变动前 */
    @TableField("before_score")
    private Integer beforeScore;

    /** 变动后 */
    @TableField("after_score")
    private Integer afterScore;

    /** 原因 */
    private String reason;

    /** 关联类型 */
    @TableField("related_type")
    private String relatedType;

    /** 关联ID */
    @TableField("related_id")
    private Long relatedId;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
