package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ff.backend.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 活动信息表(Activity)数据库访问层
 *
 * @author makejava
 * @since 2026-09-29 14:04:49
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

}

