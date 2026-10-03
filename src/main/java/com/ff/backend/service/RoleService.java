package com.ff.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.ff.backend.entity.Role;

/**
 * 角色表(Role)业务服务接口
 *
 * @author makejava
 * @since 2026-10-03 15:41:08
 */
public interface RoleService extends IService<Role> {

    Page<Role> listPage(int page, int size);
}

