package com.ff.backend.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.entity.User;
import com.ff.backend.mapper.UserMapper;
import com.ff.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)业务服务实现类
 *
 * @author makejava
 * @since 2026-10-03 15:41:19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {

    private final UserMapper userMapper;

    @Override
    public Page<User> listPage(int page, int size) {
        return this.page(
                new Page<User>(page, size),
                Wrappers.<User>lambdaQuery()
                        .orderByAsc(User::getId)
        );
    }

    @Override
    public User findByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        return userMapper.selectOne(queryWrapper);
    }
}

