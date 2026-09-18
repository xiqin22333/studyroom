package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.SysUserRole;
import org.example.studyroom.mapper.SysUserRoleMapper;
import org.example.studyroom.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色关联表 Service 实现
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {
}
