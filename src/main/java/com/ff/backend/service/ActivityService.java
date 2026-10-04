package com.ff.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.IService;
import com.ff.backend.entity.Activity;

/**
 * 活动信息表(Activity)业务服务接口
 *
 * @author makejava
 * @since 2026-10-01 11:12:38
 */
public interface ActivityService extends IService<Activity> {
    Page<Activity> listActivities(int page, int size,Long userId);
    void createActivity(Long userId, Long activityId);
    boolean haveActivity (Long userId, Long activityId);
}

