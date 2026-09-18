package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.ViolationRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 违规记录表 Mapper
 */
@Mapper
public interface ViolationRecordMapper extends BaseMapper<ViolationRecord> {
}
