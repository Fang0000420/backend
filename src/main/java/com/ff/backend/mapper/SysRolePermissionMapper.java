package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ff.backend.entity.SysRolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联表(SysRolePermission)数据库访问层
 *
 * @author makejava
 * @since 2026-10-04 20:09:13
 */
@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

}

