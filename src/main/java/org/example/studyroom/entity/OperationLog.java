package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 操作日志表
 */
@Data
@TableName("operation_log")
public class OperationLog {

    /** 日志ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 操作人 */
    @TableField("user_id")
    private Long userId;

    /** 模块 */
    private String module;

    /** 动作 */
    private String action;

    /** 请求方法 */
    private String method;

    /** 请求URL */
    private String url;

    /** IP */
    private String ip;

    /** User-Agent */
    @TableField("user_agent")
    private String userAgent;

    /** 请求参数 */
    private String params;

    /** 返回结果 */
    private String result;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
