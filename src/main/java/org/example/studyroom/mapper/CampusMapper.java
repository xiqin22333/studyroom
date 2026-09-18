package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Campus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 校区表 Mapper
 */
@Mapper
public interface CampusMapper extends BaseMapper<Campus> {
}
