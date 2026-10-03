package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ff.backend.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 权限表(Permission)数据库访问层
 *
 * @author makejava
 * @since 2026-10-03 12:25:51
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    @Select("""
    SELECT r.code AS code
    FROM sys_user_role ur
    JOIN sys_role r ON r.id = ur.role_id
    WHERE ur.user_id = #{userId}
      AND r.is_delete = 0

    UNION

    SELECT p.code AS code
    FROM sys_user_role ur
    JOIN sys_role r ON r.id = ur.role_id
    JOIN sys_role_permission rp ON rp.role_id = r.id
    JOIN sys_permission p ON p.id = rp.permission_id
    WHERE ur.user_id = #{userId}
      AND r.is_delete = 0
      AND p.is_delete = 0
    """)
    List<String> getPermissionByUserId(@Param("userId") Long userId);
}

