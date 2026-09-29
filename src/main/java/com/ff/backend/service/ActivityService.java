package com.ff.backend.service;


import com.baomidou.mybatisplus.spring.service.IService;
import com.ff.backend.entity.Activity;

/**
 * 活动信息表(Activity)业务服务接口
 *
 * @author makejava
 * @since 2026-09-29 14:04:49
 */
public interface ActivityService extends IService<Activity> {
    public int delete(int id);
}

