package com.ff.backend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.entity.Activity;
import com.ff.backend.mapper.ActivityMapper;
import com.ff.backend.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 活动信息表(Activity)业务服务实现类
 *
 * @author makejava
 * @since 2026-10-01 11:12:38
 */
@Service
@RequiredArgsConstructor
public class ActivityServiceImpl
        extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {
    private final ActivityMapper activityMapper;

    @Override
    public Page<Activity> listActivities(int page, int size) {
        return this.page(
                new Page<Activity>(page, size),
                Wrappers.<Activity>lambdaQuery()
                        .orderByAsc(Activity::getId)
        );
    }
}

