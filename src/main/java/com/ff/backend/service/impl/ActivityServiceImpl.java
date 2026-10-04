package com.ff.backend.service.impl;

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
    public Page<Activity> listActivities(int page, int size, Long userId) {
        return activityMapper.listActivities(new Page<>(page, size), userId);
    }

    @Override
    public void createActivity(Long userId, Long activityId) {
        activityMapper.createActivity(userId, activityId);
    }

    @Override
    public boolean haveActivity(Long userId, Long activityId) {
        System.out.println(userId + "+" + activityId);
        Long i = activityMapper.haveActivity(userId, activityId);
        if (i == null) {
            return false;
        }
        return i == 11 || i == 12;
    }
}

