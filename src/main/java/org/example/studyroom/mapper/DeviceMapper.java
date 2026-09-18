package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Device;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备表 Mapper
 */
@Mapper
public interface DeviceMapper extends BaseMapper<Device> {
}
