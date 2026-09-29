package com.ff.backend.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 活动信息表(Activity)数据库实体类
 *
 * @author makejava
 * @since 2026-09-29 14:04:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("activity")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 活动名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 活动描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 活动状态: 0-草稿, 1-进行中, 2-已结束, 3-已取消
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建人/负责人ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 活动开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 活动结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 逻辑删除标识: 0-未删除, 1-已删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

}

