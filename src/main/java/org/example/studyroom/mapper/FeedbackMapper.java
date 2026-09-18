package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 * 反馈报修表 Mapper
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {
}
