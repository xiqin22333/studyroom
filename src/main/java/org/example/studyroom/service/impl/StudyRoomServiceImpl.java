package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.StudyRoom;
import org.example.studyroom.mapper.StudyRoomMapper;
import org.example.studyroom.service.StudyRoomService;
import org.springframework.stereotype.Service;

/**
 * 自习室表 Service 实现
 */
@Service
public class StudyRoomServiceImpl extends ServiceImpl<StudyRoomMapper, StudyRoom> implements StudyRoomService {
}
