package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 反馈报修表
 */
@Data
@TableName("feedback")
public class Feedback {

    /** 反馈ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 自习室ID */
    @TableField("room_id")
    private Long roomId;

    /** 座位ID */
    @TableField("seat_id")
    private Long seatId;

    /** 1报修 2建议 3投诉 */
    private Integer type;

    /** 内容 */
    private String content;

    /** 图片 */
    private String images;

    /** 0待处理 1处理中 2已处理 */
    private Integer status;

    /** 回复 */
    private String reply;

    /** 处理人 */
    @TableField("handler_id")
    private Long handlerId;

    /** 处理时间 */
    @TableField("handled_at")
    private LocalDateTime handledAt;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
