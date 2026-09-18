package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.studyroom.common.BusinessException;
import org.example.studyroom.dto.LoginRequest;
import org.example.studyroom.dto.LoginResponse;
import org.example.studyroom.entity.SysUser;
import org.example.studyroom.entity.SysUserRole;
import org.example.studyroom.entity.SysRole;
import org.example.studyroom.mapper.SysUserMapper;
import org.example.studyroom.mapper.SysUserRoleMapper;
import org.example.studyroom.mapper.SysRoleMapper;
import org.example.studyroom.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户表 Service 实现
 * 说明：密码使用 SHA-256 摘要存储（学习项目简化方案，生产环境建议改为 BCrypt）
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;

    /** 默认密码 */
    private static final String DEFAULT_PASSWORD = "123456";

    @Override
    public LoginResponse login(LoginRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()) {
            throw new BusinessException("用户名或密码不能为空");
        }
        SysUser user = lambdaQuery().eq(SysUser::getUsername, request.getUsername().trim()).one();
        if (user == null || !encodePassword(request.getPassword()).equals(user.getPasswordHash())) {
            throw new BusinessException("用户名或密码错误");
        }
        if (user.getStatus() != null && user.getStatus() != 1) {
            throw new BusinessException("账号已被禁用，无法登录");
        }
        // 更新最后登录时间
        SysUser update = new SysUser();
        update.setId(user.getId());
        update.setLastLoginAt(LocalDateTime.now());
        updateById(update);
        user.setLastLoginAt(LocalDateTime.now());
        // 查询角色
        List<String> roles = sysUserRoleMapper.selectRoleCodesByUserId(user.getId());
        return new LoginResponse(user, roles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addUser(SysUser user, List<Long> roleIds) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BusinessException("用户名不能为空");
        }
        long exists = lambdaQuery().eq(SysUser::getUsername, user.getUsername().trim()).count();
        if (exists > 0) {
            throw new BusinessException("用户名已存在");
        }
        String rawPassword = (user.getPasswordHash() == null || user.getPasswordHash().isBlank())
                ? DEFAULT_PASSWORD : user.getPasswordHash();
        user.setUsername(user.getUsername().trim());
        user.setPasswordHash(encodePassword(rawPassword));
        if (user.getCreditScore() == null) {
            user.setCreditScore(100);
        }
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        if (user.getUserType() == null) {
            user.setUserType(1);
        }
        user.setId(null);
        save(user);
        saveUserRoles(user.getId(), roleIds);
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUser user, List<Long> roleIds) {
        if (user.getId() == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (user.getPasswordHash() != null && !user.getPasswordHash().isBlank()) {
            // 传了密码才重新加密
            user.setPasswordHash(encodePassword(user.getPasswordHash()));
        } else {
            // 没传密码就保持原密码不变
            user.setPasswordHash(null);
        }
        updateById(user);
        if (roleIds != null) {
            saveUserRoles(user.getId(), roleIds);
        }
        return true;
    }

    @Override
    public boolean resetPassword(Long id, String newPassword) {
        String raw = (newPassword == null || newPassword.isBlank()) ? DEFAULT_PASSWORD : newPassword;
        SysUser update = new SysUser();
        update.setId(id);
        update.setPasswordHash(encodePassword(raw));
        return updateById(update);
    }

    @Override
    public boolean changeStatus(Long id, Integer status) {
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException("状态值不正确，只能为 0（禁用）或 1（正常）");
        }
        SysUser update = new SysUser();
        update.setId(id);
        update.setStatus(status);
        return updateById(update);
    }

    @Override
    public List<Long> getRoleIds(Long userId) {
        return sysUserRoleMapper.selectRoleIdsByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerStudent(SysUser user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new BusinessException("用户名不能为空");
        }
        if (user.getPasswordHash() == null || user.getPasswordHash().length() < 6) {
            throw new BusinessException("密码至少 6 位");
        }
        if (user.getRealName() == null || user.getRealName().isBlank()) {
            throw new BusinessException("姓名不能为空");
        }
        if (user.getStudentNo() == null || user.getStudentNo().isBlank()) {
            throw new BusinessException("学号不能为空");
        }
        // 校验用户名唯一
        long nameExists = lambdaQuery().eq(SysUser::getUsername, user.getUsername().trim()).count();
        if (nameExists > 0) {
            throw new BusinessException("用户名已存在");
        }
        // 校验学号唯一
        long noExists = lambdaQuery().eq(SysUser::getStudentNo, user.getStudentNo().trim()).count();
        if (noExists > 0) {
            throw new BusinessException("学号已被注册");
        }
        // 强制学生属性
        user.setId(null);
        user.setUsername(user.getUsername().trim());
        user.setStudentNo(user.getStudentNo().trim());
        user.setPasswordHash(encodePassword(user.getPasswordHash()));
        user.setUserType(1);
        user.setStatus(1);
        user.setCreditScore(100);
        save(user);
        // 自动分配 STUDENT 角色
        SysRole studentRole = sysRoleMapper.selectOne(
                Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleCode, "STUDENT").last("LIMIT 1"));
        if (studentRole != null) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(studentRole.getId());
            sysUserRoleMapper.insert(ur);
        }
        return user.getId();
    }

    /** 重新设置某用户的角色（先删旧的，再插新的） */
    private void saveUserRoles(Long userId, List<Long> roleIds) {
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getUserId, userId));
        if (roleIds != null && !roleIds.isEmpty()) {
            for (Long roleId : roleIds) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }

    /** 密码加密：SHA-256 摘要（使用 JDK 自带算法） */
    private String encodePassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 算法不可用", e);
        }
    }
}
