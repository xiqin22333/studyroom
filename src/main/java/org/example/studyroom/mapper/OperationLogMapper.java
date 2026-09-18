package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志表 Mapper
 */
@Mapper
public interface OperationLogMapper extends BaseMapper<OperationLog> {
}
