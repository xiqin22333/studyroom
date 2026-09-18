package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Notification;
import org.example.studyroom.mapper.NotificationMapper;
import org.example.studyroom.service.NotificationService;
import org.springframework.stereotype.Service;

/**
 * 通知表 Service 实现
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {
}
