package com.ff.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.ff.backend.entity.Permission;

import java.util.List;

/**
 * 权限表(Permission)业务服务接口
 *
 * @author makejava
 * @since 2026-10-03 15:40:49
 */
public interface PermissionService extends IService<Permission> {

    Page<Permission> listPage(int page, int size);

    List<String> getPermissionByUserId(Long userId);
}

