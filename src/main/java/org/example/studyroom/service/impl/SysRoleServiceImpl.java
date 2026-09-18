package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.SysRole;
import org.example.studyroom.mapper.SysRoleMapper;
import org.example.studyroom.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * 角色表 Service 实现
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
