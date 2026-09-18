package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.ViolationRecord;
import org.example.studyroom.mapper.ViolationRecordMapper;
import org.example.studyroom.service.ViolationRecordService;
import org.springframework.stereotype.Service;

/**
 * 违规记录表 Service 实现
 */
@Service
public class ViolationRecordServiceImpl extends ServiceImpl<ViolationRecordMapper, ViolationRecord> implements ViolationRecordService {
}
