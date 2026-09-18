package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 校区表
 */
@Data
@TableName("campus")
public class Campus {

    /** 校区ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 校区编码 */
    @TableField("campus_code")
    private String campusCode;

    /** 校区名称 */
    @TableField("campus_name")
    private String campusName;

    /** 地址 */
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
