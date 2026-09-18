package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 楼栋表
 */
@Data
@TableName("building")
public class Building {

    /** 楼栋ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 所属校区 */
    @TableField("campus_id")
    private Long campusId;

    /** 楼栋编码 */
    @TableField("building_code")
    private String buildingCode;

    /** 楼栋名称 */
    @TableField("building_name")
    private String buildingName;

    /** 楼层数 */
    @TableField("floor_count")
    private Integer floorCount;

    /** 详细地址 */
    private String address;

    /** 1启用 0停用 */
    private Integer status;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
