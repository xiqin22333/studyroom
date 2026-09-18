package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Data;

/**
 * 自习室表
 */
@Data
@TableName("study_room")
public class StudyRoom {

    /** 自习室ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 所属楼栋 */
    @TableField("building_id")
    private Long buildingId;

    /** 房间号 */
    @TableField("room_no")
    private String roomNo;

    /** 自习室名称 */
    @TableField("room_name")
    private String roomName;

    /** 楼层 */
    private Integer floor;

    /** 座位数 */
    private Integer capacity;

    /** 开放开始时间 */
    @TableField("open_time")
    private LocalTime openTime;

    /** 开放结束时间 */
    @TableField("close_time")
    private LocalTime closeTime;

    /** 1开放 0关闭 2维护 */
    private Integer status;

    /** 描述 */
    private String description;

    /** 图片 */
    @TableField("image_url")
    private String imageUrl;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
