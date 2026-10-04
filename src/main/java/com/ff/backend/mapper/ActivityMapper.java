package com.ff.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ff.backend.entity.Activity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 活动信息表(Activity)数据库访问层
 *
 * @author makejava
 * @since 2026-10-01 11:12:38
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    @Insert("""
    insert into activity_user_role (activity_id,user_id,role_id) values (#{activityId},#{userId},10)
    """)
    void createActivity(@Param("userId") Long userId,@Param("activityId")Long activityId);
    @Select("""
    SELECT a.*
    FROM activity a
    WHERE a.id IN (
        SELECT activity_id
        FROM activity_user_role
        WHERE user_id = #{userId}
    )
    ORDER BY a.id ASC
    """)
    Page<Activity> listActivities(Page<Activity> page, @Param("userId") Long userId);
    @Select("""
    select role_id from activity_user_role where user_id = #{activityId} and activity_id = #{userId}
    """)
    Long haveActivity(@Param("userId") Long userId,@Param("activityId")Long activityId);
}

