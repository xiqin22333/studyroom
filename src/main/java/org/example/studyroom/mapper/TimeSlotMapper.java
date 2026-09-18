package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.TimeSlot;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约时段表 Mapper
 */
@Mapper
public interface TimeSlotMapper extends BaseMapper<TimeSlot> {
}
