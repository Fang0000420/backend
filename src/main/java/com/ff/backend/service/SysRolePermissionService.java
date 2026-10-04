package com.ff.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.ff.backend.entity.SysRolePermission;

/**
 * 角色权限关联表(SysRolePermission)业务服务接口
 *
 * @author makejava
 * @since 2026-10-04 20:19:23
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    Page<SysRolePermission> listPage(int page, int size);
}

