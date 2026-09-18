package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.TimeSlot;
import org.example.studyroom.mapper.TimeSlotMapper;
import org.example.studyroom.service.TimeSlotService;
import org.springframework.stereotype.Service;

/**
 * 预约时段表 Service 实现
 */
@Service
public class TimeSlotServiceImpl extends ServiceImpl<TimeSlotMapper, TimeSlot> implements TimeSlotService {
}
