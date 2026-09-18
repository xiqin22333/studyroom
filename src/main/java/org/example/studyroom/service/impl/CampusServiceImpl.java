package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Campus;
import org.example.studyroom.mapper.CampusMapper;
import org.example.studyroom.service.CampusService;
import org.springframework.stereotype.Service;

/**
 * 校区表 Service 实现
 */
@Service
public class CampusServiceImpl extends ServiceImpl<CampusMapper, Campus> implements CampusService {
}
