package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 通知表
 */
@Data
@TableName("notification")
public class Notification {

    /** 通知ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 接收用户 */
    @TableField("user_id")
    private Long userId;

    /** 标题 */
    private String title;

    /** 内容 */
    private String content;

    /** 1预约 2签到 3违规 4系统 */
    private Integer type;

    /** 是否已读 */
    @TableField("is_read")
    private Integer isRead;

    /** 阅读时间 */
    @TableField("read_at")
    private LocalDateTime readAt;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
