package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Notification;
import org.apache.ibatis.annotations.Mapper;

/**
 * 通知表 Mapper
 */
@Mapper
public interface NotificationMapper extends BaseMapper<Notification> {
}
