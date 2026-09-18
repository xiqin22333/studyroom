package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.OperationLog;
import org.example.studyroom.mapper.OperationLogMapper;
import org.example.studyroom.service.OperationLogService;
import org.springframework.stereotype.Service;

/**
 * 操作日志表 Service 实现
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
