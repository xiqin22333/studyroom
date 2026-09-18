package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.StudyRoom;
import org.apache.ibatis.annotations.Mapper;

/**
 * 自习室表 Mapper
 */
@Mapper
public interface StudyRoomMapper extends BaseMapper<StudyRoom> {
}
