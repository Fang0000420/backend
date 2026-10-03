package com.ff.backend.dto.request;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 活动信息表(Activity)修改请求 DTO
 *
 * @author makejava
 * @since 2026-10-01 11:12:38
 */
@Data
public class ActivityUpdateRequest {

    /**
     * 活动名称
     */
    @NotNull
    private String name;

    /**
     * 活动描述
     */
    @NotNull
    private String description;

    /**
     * 活动状态: 0-草稿, 1-进行中, 2-已结束, 3-已取消
     */
    @NotNull
    private Integer status;

    /**
     * 创建人/负责人ID
     */
    private Long userId;

    /**
     * 活动开始时间
     */
    @NotNull
    private Date startTime;

    /**
     * 活动结束时间
     */
    @NotNull
    private Date endTime;

    /**
     * 逻辑删除标识: 0-未删除, 1-已删除
     */
    private Integer isDeleted;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 活动容量
     */
    @NotNull(message = "容量不能为空")
    @Min(value = 1, message = "容量必须大于0")
    @Max(value = 10001, message = "容量必须小于等于10000")
    private Integer capacity;

}

