package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ff.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表(User)数据库访问层
 *
 * @author makejava
 * @since 2026-10-03 12:28:14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

