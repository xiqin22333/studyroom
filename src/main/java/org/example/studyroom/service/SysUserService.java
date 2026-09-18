package org.example.studyroom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import org.example.studyroom.dto.LoginRequest;
import org.example.studyroom.dto.LoginResponse;
import org.example.studyroom.entity.SysUser;

/**
 * 用户表 Service
 */
public interface SysUserService extends IService<SysUser> {

    /** 登录：校验用户名密码，成功返回用户信息 + 角色 */
    LoginResponse login(LoginRequest request);

    /** 新增用户（密码加密存储，并分配角色） */
    Long addUser(SysUser user, List<Long> roleIds);

    /** 修改用户（传了密码才改密码，传了 roleIds 才重新分配角色） */
    boolean updateUser(SysUser user, List<Long> roleIds);

    /** 重置密码（newPassword 为空则重置为 123456） */
    boolean resetPassword(Long id, String newPassword);

    /** 启用/禁用账号（status: 1正常 0禁用） */
    boolean changeStatus(Long id, Integer status);

    /** 查询某用户的角色ID列表 */
    List<Long> getRoleIds(Long userId);

    /** 学生自助注册（自动分配 STUDENT 角色） */
    Long registerStudent(SysUser user);
}
