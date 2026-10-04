package com.ff.backend.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.dto.response.PermissionResponse;
import com.ff.backend.dto.response.RoleResponse;
import com.ff.backend.entity.Role;
import com.ff.backend.entity.SysRolePermission;
import com.ff.backend.mapper.PermissionMapper;
import com.ff.backend.mapper.RoleMapper;
import com.ff.backend.mapper.SysRolePermissionMapper;
import com.ff.backend.security.LoginUser;
import com.ff.backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final PermissionMapper permissionMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public Page<Role> listPage(int page, int size) {
        return this.page(
                new Page<Role>(page, size),
                Wrappers.<Role>lambdaQuery()
                        .orderByAsc(Role::getId)
        );
    }

    @Override
    public RoleResponse getRoleByUser(Long userId) {
        Role role = roleMapper.getRoleByUserId(userId);
        List<PermissionResponse> permissionByRole = permissionMapper.getPermissionByRole(role.getId());
        List<Long> permissionsByRoleId = permissionMapper.getPermissionsByRoleId(role.getId());
        RoleResponse response = new RoleResponse();
        // 角色的基本信息来自 role
        response.setId(role.getId());
        response.setName(role.getName());
        response.setCode(role.getCode());
        response.setDescription(role.getDescription());
        response.setCreateTime(role.getCreateTime());
        response.setUpdateTime(role.getUpdateTime());
        response.setIsDelete(role.getIsDelete());

        // 权限列表已经是 List<PermissionResponse>，直接赋值
        response.setPermissions(permissionByRole);
        response.setPermissionIds(permissionsByRoleId);
        return response;
    }

    @Override
    public boolean hasPermission(List<Long> permissionIds, LoginUser currentUser) {
        Long userId = currentUser.getUserId();
        Role role = roleMapper.getRoleByUserId(userId);
        List<Long> permissionsByRoleId = permissionMapper.getPermissionsByRoleId(role.getId());
        boolean contain = true;
        for (Long permission : permissionIds) {
            if (!permissionsByRoleId.contains(permission)) {
                contain = false;
                break;
            }
        }
        return contain;
    }


    @Override
    public void updateRolePermission(Long roleId, List<Long> permissionIds) {
        sysRolePermissionMapper.delete(Wrappers.<SysRolePermission>lambdaQuery().eq(SysRolePermission::getRoleId, roleId));
        List<SysRolePermission> relations = permissionIds.stream()
                .distinct()
                .map(permissionId -> {
                    SysRolePermission relation = new SysRolePermission();
                    relation.setRoleId(roleId);
                    relation.setPermissionId(permissionId);
                    return relation;
                })
                .toList();

        if (!relations.isEmpty()) {
            sysRolePermissionMapper.insert(relations);
        }
    }
}

