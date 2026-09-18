package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Blacklist;
import org.example.studyroom.mapper.BlacklistMapper;
import org.example.studyroom.service.BlacklistService;
import org.springframework.stereotype.Service;

/**
 * 黑名单表 Service 实现
 */
@Service
public class BlacklistServiceImpl extends ServiceImpl<BlacklistMapper, Blacklist> implements BlacklistService {
}
