package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Building;
import org.apache.ibatis.annotations.Mapper;

/**
 * 楼栋表 Mapper
 */
@Mapper
public interface BuildingMapper extends BaseMapper<Building> {
}
