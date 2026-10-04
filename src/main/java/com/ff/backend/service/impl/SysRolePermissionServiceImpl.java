package com.ff.backend.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.entity.SysRolePermission;
import com.ff.backend.mapper.SysRolePermissionMapper;
import com.ff.backend.service.SysRolePermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 角色权限关联表(SysRolePermission)业务服务实现类
 *
 * @author makejava
 * @since 2026-10-04 20:19:23
 */
@Service
@RequiredArgsConstructor
public class SysRolePermissionServiceImpl
        extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements SysRolePermissionService {

    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public Page<SysRolePermission> listPage(int page, int size) {
        return this.page(
                new Page<SysRolePermission>(page, size),
                Wrappers.<SysRolePermission>lambdaQuery()
                        .orderByAsc(SysRolePermission::getRoleId)
        );
    }
}

