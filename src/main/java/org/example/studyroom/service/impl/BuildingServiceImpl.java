package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Building;
import org.example.studyroom.mapper.BuildingMapper;
import org.example.studyroom.service.BuildingService;
import org.springframework.stereotype.Service;

/**
 * 楼栋表 Service 实现
 */
@Service
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements BuildingService {
}
