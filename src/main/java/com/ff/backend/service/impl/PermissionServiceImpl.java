package com.ff.backend.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.entity.Permission;
import com.ff.backend.mapper.PermissionMapper;
import com.ff.backend.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 权限表(Permission)业务服务实现类
 *
 * @author makejava
 * @since 2026-10-03 15:40:49
 */
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl
        extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionService {

    private final PermissionMapper permissionMapper;

    @Override
    public Page<Permission> listPage(int page, int size) {
        return this.page(
                new Page<Permission>(page, size),
                Wrappers.<Permission>lambdaQuery()
                        .orderByAsc(Permission::getId)
        );
    }

    @Override
    public List<String> getPermissionByUserId(Long userId) {
        return permissionMapper.getPermissionByUserId(userId);
    }
}

