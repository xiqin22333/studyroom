package org.example.studyroom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.studyroom.entity.Device;
import org.example.studyroom.mapper.DeviceMapper;
import org.example.studyroom.service.DeviceService;
import org.springframework.stereotype.Service;

/**
 * 设备表 Service 实现
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {
}
