package org.example.studyroom.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * 用户表
 */
@Data
@TableName("sys_user")
public class SysUser {

    /** 用户ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 登录名 */
    private String username;

    /** 密码哈希 */
    @TableField("password_hash")
    private String passwordHash;

    /** 真实姓名 */
    @TableField("real_name")
    private String realName;

    /** 学号/工号 */
    @TableField("student_no")
    private String studentNo;

    /** 1学生 2教师 3管理员 */
    @TableField("user_type")
    private Integer userType;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像 */
    @TableField("avatar_url")
    private String avatarUrl;

    /** 学院 */
    private String college;

    /** 专业 */
    private String major;

    /** 班级 */
    @TableField("class_name")
    private String className;

    /** 信用分 */
    @TableField("credit_score")
    private Integer creditScore;

    /** 1正常 0禁用 2黑名单 */
    private Integer status;

    /** 最后登录时间 */
    @TableField("last_login_at")
    private LocalDateTime lastLoginAt;

    /** 创建时间 */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /** 更新时间 */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /** 逻辑删除时间（加 @TableLogic 后，删除变成"打标记"，查列表时自动过滤已删除）
     *  value = "NULL"：未删除时字段为 NULL（查询条件自动变成 deleted_at IS NULL）
     *  delval = "now()"：删除时自动写入当前时间 */
    @TableField("deleted_at")
    @TableLogic(value = "NULL", delval = "now()")
    private LocalDateTime deletedAt;

    /** 该用户拥有的角色ID列表（非数据库字段，用于新增/修改时接收前端传的角色） */
    @TableField(exist = false)
    private List<Long> roleIds;
}
