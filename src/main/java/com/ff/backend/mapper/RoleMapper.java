package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ff.backend.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表(Role)数据库访问层
 *
 * @author makejava
 * @since 2026-10-03 12:27:11
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}

