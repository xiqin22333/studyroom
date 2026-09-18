package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.ReservationRule;
import org.example.studyroom.mapper.ReservationRuleMapper;
import org.example.studyroom.service.ReservationRuleService;
import org.springframework.stereotype.Service;

/**
 * 预约规则表 Service 实现
 */
@Service
public class ReservationRuleServiceImpl extends ServiceImpl<ReservationRuleMapper, ReservationRule> implements ReservationRuleService {
}
