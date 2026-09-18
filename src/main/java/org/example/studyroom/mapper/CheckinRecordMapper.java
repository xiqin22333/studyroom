package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.CheckinRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 签到记录表 Mapper
 */
@Mapper
public interface CheckinRecordMapper extends BaseMapper<CheckinRecord> {
}
