package com.ff.backend.dto.request;

import java.util.Date;

import lombok.Data;

/**
 * 活动信息表(Activity)新增请求 DTO
 *
 * @author makejava
 * @since 2026-09-29 14:04:50
 */
@Data
public class ActivityCreateRequest {

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动状态: 0-草稿, 1-进行中, 2-已结束, 3-已取消
     */
    private Integer status;

    /**
     * 创建人/负责人ID
     */
    private Long userId;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 逻辑删除标识: 0-未删除, 1-已删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}

