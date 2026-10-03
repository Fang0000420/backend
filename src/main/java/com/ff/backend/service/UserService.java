package com.ff.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.ff.backend.entity.User;

/**
 * 用户表(User)业务服务接口
 *
 * @author makejava
 * @since 2026-10-03 15:41:19
 */
public interface UserService extends IService<User> {

    Page<User> listPage(int page, int size);

    User findByUsername(String username);
}

