package org.example.studyroom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.studyroom.entity.Blacklist;
import org.apache.ibatis.annotations.Mapper;

/**
 * 黑名单表 Mapper
 */
@Mapper
public interface BlacklistMapper extends BaseMapper<Blacklist> {
}
