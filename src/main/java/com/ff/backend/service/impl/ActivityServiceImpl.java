package com.ff.backend.service.impl;


import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.ff.backend.entity.Activity;
import com.ff.backend.mapper.ActivityMapper;
import com.ff.backend.service.ActivityService;
import org.springframework.stereotype.Service;

/**
 * 活动信息表(Activity)业务服务实现类
 *
 * @author makejava
 * @since 2026-09-29 14:04:49
 */
@Service
public class ActivityServiceImpl
        extends ServiceImpl<ActivityMapper, Activity>
        implements ActivityService {

    @Override
    public int delete(int id) {
        return 0;
    }
}

