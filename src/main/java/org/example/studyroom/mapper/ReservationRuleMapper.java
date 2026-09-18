package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.ReservationRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约规则表 Mapper
 */
@Mapper
public interface ReservationRuleMapper extends BaseMapper<ReservationRule> {
}
