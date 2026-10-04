package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ff.backend.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 角色表(Role)数据库访问层
 *
 * @author makejava
 * @since 2026-10-03 12:27:11
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    @Select("""
            SELECT r.*
            FROM sys_user_role ur
            JOIN sys_role r ON r.id = ur.role_id
            WHERE ur.user_id = #{userId}
              AND r.is_delete = 0
            """)
    Role getRoleByUserId(@Param("userId") Long userId);
}

