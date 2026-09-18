package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户角色关联表
 */
@Data
@TableName("sys_user_role")
public class SysUserRole {

    /** 主键 */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    @TableField("user_id")
    private Long userId;

    /** 角色ID */
    @TableField("role_id")
    private Long roleId;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;
}
