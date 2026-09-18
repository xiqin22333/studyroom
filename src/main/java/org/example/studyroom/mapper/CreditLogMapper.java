package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.CreditLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 信用分流水表 Mapper
 */
@Mapper
public interface CreditLogMapper extends BaseMapper<CreditLog> {
}
