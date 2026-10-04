package com.ff.backend.dto.request;

import java.util.Date;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ff.backend.entity.Activity;

/**
 * 活动信息表(Activity)新增请求 DTO
 *
 * @author makejava
 * @since 2026-10-01 11:12:38
 */
@Data
@NoArgsConstructor
public class ActivityCreateRequest {

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
     * 创建时间
     */
    private Date createTime;

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

    /** 从实体复制对应字段；实体为 null 时，各字段保留 null。 */
    public ActivityCreateRequest(Activity entity) {
        if (entity == null) {
            return;
        }
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.status = entity.getStatus();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
        this.isDeleted = entity.getIsDeleted();
        this.createTime = entity.getCreateTime();
        this.updateTime = entity.getUpdateTime();
        this.capacity = entity.getCapacity();
    }

}

