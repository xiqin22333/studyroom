package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 系统配置表
 */
@Data
@TableName("sys_config")
public class SysConfig {

    /** 配置ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 配置键 */
    @TableField("config_key")
    private String configKey;

    /** 配置值 */
    @TableField("config_value")
    private String configValue;

    /** 描述 */
    private String description;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
