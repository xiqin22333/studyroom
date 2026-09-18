package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.SysConfig;
import org.example.studyroom.mapper.SysConfigMapper;
import org.example.studyroom.service.SysConfigService;
import org.springframework.stereotype.Service;

/**
 * 系统配置表 Service 实现
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
}
