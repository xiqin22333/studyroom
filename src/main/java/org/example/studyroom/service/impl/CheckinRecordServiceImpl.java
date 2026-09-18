package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.CheckinRecord;
import org.example.studyroom.mapper.CheckinRecordMapper;
import org.example.studyroom.service.CheckinRecordService;
import org.springframework.stereotype.Service;

/**
 * 签到记录表 Service 实现
 */
@Service
public class CheckinRecordServiceImpl extends ServiceImpl<CheckinRecordMapper, CheckinRecord> implements CheckinRecordService {
}
