package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 座位表
 */
@Data
@TableName("seat")
public class Seat {

    /** 座位ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 所属自习室 */
    @TableField("room_id")
    private Long roomId;

    /** 座位号 */
    @TableField("seat_no")
    private String seatNo;

    /** 行 */
    @TableField("row_no")
    private Integer rowNo;

    /** 列 */
    @TableField("col_no")
    private Integer colNo;

    /** 1普通 2电源 3靠窗 4研讨 */
    @TableField("seat_type")
    private Integer seatType;

    /** 是否有电源 */
    @TableField("has_power")
    private Integer hasPower;

    /** 是否靠窗 */
    @TableField("has_window")
    private Integer hasWindow;

    /** 1可用 0维修 2停用 */
    private Integer status;

    /** 描述 */
    private String description;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
