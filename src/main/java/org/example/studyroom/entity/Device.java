package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 设备表
 */
@Data
@TableName("device")
public class Device {

    /** 设备ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 所属自习室 */
    @TableField("room_id")
    private Long roomId;

    /** 设备编码 */
    @TableField("device_code")
    private String deviceCode;

    /** 设备名称 */
    @TableField("device_name")
    private String deviceName;

    /** 1闸机 2扫码器 3人脸机 */
    @TableField("device_type")
    private Integer deviceType;

    /** 1启用 0停用 */
    private Integer status;

    /** 最后在线时间 */
    @TableField("last_online_at")
    private LocalDateTime lastOnlineAt;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
