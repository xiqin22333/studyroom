package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约记录表 Mapper
 */
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
}
