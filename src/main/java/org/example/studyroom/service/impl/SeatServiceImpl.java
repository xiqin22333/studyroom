package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Seat;
import org.example.studyroom.mapper.SeatMapper;
import org.example.studyroom.service.SeatService;
import org.springframework.stereotype.Service;

/**
 * 座位表 Service 实现
 */
@Service
public class SeatServiceImpl extends ServiceImpl<SeatMapper, Seat> implements SeatService {
}
