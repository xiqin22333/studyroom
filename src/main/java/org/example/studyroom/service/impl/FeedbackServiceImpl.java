package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Feedback;
import org.example.studyroom.mapper.FeedbackMapper;
import org.example.studyroom.service.FeedbackService;
import org.springframework.stereotype.Service;

/**
 * 反馈报修表 Service 实现
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {
}
