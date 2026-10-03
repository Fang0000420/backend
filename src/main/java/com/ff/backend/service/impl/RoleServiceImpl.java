package com.ff.backend.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.entity.Role;
import com.ff.backend.mapper.RoleMapper;
import com.ff.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 角色表(Role)业务服务实现类
 *
 * @author makejava
 * @since 2026-10-03 15:41:08
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl
        extends ServiceImpl<RoleMapper, Role>
        implements RoleService {
    private final RoleMapper roleMapper;

    @Override
    public Page<Role> listPage(int page, int size) {
        return this.page(
                new Page<Role>(page, size),
                Wrappers.<Role>lambdaQuery()
                        .orderByAsc(Role::getId)
        );
    }
}

