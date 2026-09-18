package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Seat;
import org.apache.ibatis.annotations.Mapper;

/**
 * 座位表 Mapper
 */
@Mapper
public interface SeatMapper extends BaseMapper<Seat> {
}
